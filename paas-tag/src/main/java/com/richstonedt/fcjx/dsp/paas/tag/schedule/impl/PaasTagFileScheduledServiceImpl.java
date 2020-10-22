package com.richstonedt.fcjx.dsp.paas.tag.schedule.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.richstonedt.fcjx.dsp.common.redis.RedisLock;
import com.richstonedt.fcjx.dsp.common.util.FtpUtil;
import com.richstonedt.fcjx.dsp.paas.tag.constant.EnumRedisKey;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagFileBO;
import com.richstonedt.fcjx.dsp.paas.tag.repository.TagBitmapRepository;
import com.richstonedt.fcjx.dsp.paas.tag.schedule.IPaasTagFileScheduledService;
import com.richstonedt.fcjx.dsp.paas.tag.service.IPaasTagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * <b><code>PaasTagFileScheduledServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 3:24 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Slf4j
@Component
@RefreshScope
public class PaasTagFileScheduledServiceImpl implements IPaasTagFileScheduledService {

    @Value("${paas.tag.ftp.host}")
    private String ftpHost;

    @Value("${paas.tag.ftp.username}")
    private String ftpUsername;

    @Value("${paas.tag.ftp.password}")
    private String ftpPassword;

    @Value("${paas.tag.ftp.port}")
    private int ftpPort;

    @Value("${paas.tag.ftp.filepath}")
    private String filepath;

    @Value("${paas.tag.ftp.localpath}")
    private String localpath;

    @Value("${paas.tag.ftp.file-extension}")
    private String fileExtension;

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setDaemon(true)
            .setUncaughtExceptionHandler((t, e) -> log.error("线程池发生了异常：{}， {}", t, e))
            .setNameFormat("PaasTagFileScheduledServiceImpl thread")
            .build();
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10, 10, 500L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(100), threadFactory);

    private final RedisLock redisLock;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TagBitmapRepository tagBitmapRepository;
    private final IPaasTagService paasTagService;

    public PaasTagFileScheduledServiceImpl(RedisLock redisLock, RedisTemplate<String, Object> redisTemplate, TagBitmapRepository tagBitmapRepository, IPaasTagService paasTagService) {
        this.redisLock = redisLock;
        this.redisTemplate = redisTemplate;
        this.tagBitmapRepository = tagBitmapRepository;
        this.paasTagService = paasTagService;
    }

    @Override
    public void scheduled() {
        String key = EnumRedisKey.PAAS_SCHEDULED_SERVICE_REDIS_LOCK.getKey();
        String value = UUID.randomUUID().toString();
        try {
            Boolean lock = redisLock.tryLock(key, value, 60 * 60 * 1000L);
            Assert.isTrue(lock, "获取分布式锁失败");

            downloadFile();

            log.info("开始解析号码");
            log.info("本地文件路径:{}", localpath);
            List<File> files = getPaasTagFileFromLocal(localpath, fileExtension);
            log.info("本地PaaS标签文件:{}", files);
            parseFile(files);
        } finally {
            redisLock.unlock(key, value);
        }
    }

    @Override
    public void downloadFile() {
        try (FtpUtil ftp = FtpUtil.getClient(ftpHost, ftpPort, ftpUsername, ftpPassword); ) {
            downloadPaasTagFileFromFtp(ftp, filepath, fileExtension);
            deleteDownloadedPaasTagFileFromFtp(ftp, filepath, fileExtension);
        } catch (Exception e) {
            log.info("读取ftp文件时发生了严重异常:{}", e.getMessage());
        }
    }

    @Override
    public void parseFile(List<File> files) {
        CompletableFuture[] futures = files.stream()
                .map(f -> CompletableFuture.supplyAsync(() -> {
                    try {
                        log.info("开始解析号码包:{}", f.getName());
                        parseFile(f);
                        // 删除本地文件（改后缀名）
                        log.info("标记号码包{}已被解析过", f.getName());
                        rename(f, fName -> fName + System.currentTimeMillis());
                    } catch (IOException e) {
                        log.info("解析文件时发生了严重异常:{}", e.getMessage());
                    }
                    return null;
                }, executor))
                .toArray(CompletableFuture[]::new);

        log.info("阻塞等待所有结果执行完毕，防止定时任务重启");

        // 阻塞等待所有结果执行完毕，防止定时任务重启
        CompletableFuture.allOf(futures).join();

        log.info("本次解析号码包执行完毕");
    }

    private void parseFile(File f) throws IOException {
        log.info("正在读文件{}", f.getName());
        try (LineIterator lineIterator = FileUtils.lineIterator(f, "utf-8")) {
            int i = 0;
            Map<String, PaasTagFileBO> map = Maps.newHashMap();
            while (lineIterator.hasNext()) {
                String line = lineIterator.nextLine();

                String[] split = StringUtils.split(line, ",");
                Assert.isTrue(split != null && 2 == split.length, "分割【号码】时，发现PAAS文件内容格式有问题");

                String phone = split[0];
                String decryptPhone = paasTagService.decrypt(phone).orElseThrow(() -> new RuntimeException("加解密失败"));

                String[] orderAndTag = StringUtils.split(split[1], "_");
                Assert.isTrue(orderAndTag != null && 2 == orderAndTag.length, "分割【工单_标签】时，发现PAAS文件内容格式有问题");

                String tag = orderAndTag[1];

                generatePaasTagFileBO(map, tag, decryptPhone);

                if (++i % 100000 == 0 || !lineIterator.hasNext()) {
                    log.info("==========读取paas号码文件:{}，正在入缓存：i={}==========", f.getName(), i);
                    putBitmap(map);
                }
            }

            saveBitmap(map.keySet());
        }
    }

    private void rename(File f, Function<String, String> fn) {
        f.renameTo(new File(fn.apply(f.getName())));
        f.delete();
    }

    /**
     * 组装paas标签业务对象，标签名-》号码列表
     * @param map
     * @param tag
     * @param phone
     */
    private void generatePaasTagFileBO(Map<String, PaasTagFileBO> map, String tag, String phone) {
        PaasTagFileBO tagFileBO = map.get(tag);
        Supplier<PaasTagFileBO> newTagFileBO = () -> PaasTagFileBO.builder().tag(tag).phone(Lists.newArrayList()).build();
        tagFileBO = Optional.ofNullable(tagFileBO).orElseGet(newTagFileBO);
        tagFileBO.getPhone().add(phone);
        map.put(tag, tagFileBO);
    }

    private void saveBitmap(Set<String> tags) {
        for (String tag : tags) {
            tagBitmapRepository.saveBitmap(tag);
        }
    }

    private void putBitmap(Map<String, PaasTagFileBO> map) {
        Collection<PaasTagFileBO> values = map.values();
        for (PaasTagFileBO tagFileBO : values) {
            String tagName = tagFileBO.getTag();
            List<String> phone = tagFileBO.getPhone();
            for (String num : phone) {
                long l = Long.parseLong(num);
                tagBitmapRepository.putBitmap(tagName, l);
            }
            tagFileBO.setPhone(Lists.newArrayList());
        }
    }

    private List<File> getPaasTagFileFromLocal(String localpath, String fileExtension) {
        Predicate<File> filePredicate = f -> f.isFile() && fileExtension.equalsIgnoreCase(FilenameUtils.getExtension(f.getName()));
        File file = new File(localpath);
        File[] tempList = file.listFiles();
        Assert.notNull(tempList, "非法文件夹");
        return Arrays.stream(tempList).filter(filePredicate).collect(toList());
    }

    /**
     * 从ftp下载文件到本地
     */
    private void downloadPaasTagFileFromFtp(FtpUtil ftp, String filepath, String fileExtension) throws IOException {
        List<FTPFile> allFtpFiles = getFtpFile(ftp, filepath);
        List<FTPFile> ftpFileList = filterDownloadedFtpFiles(allFtpFiles, fileExtension);

        // 开始下载文件
        ftp.download(ftpFileList);

        // 标识为已下载文件
        ftpFileList.forEach(f -> setDowloadedFile(f.getName(), f.getSize()));
    }

    /**
     * 删除已下载文件
     * @param ftp
     * @param filepath
     * @param fileExtension
     * @throws IOException
     */
    private void deleteDownloadedPaasTagFileFromFtp(FtpUtil ftp, String filepath, String fileExtension) throws IOException {
        List<FTPFile> allFtpFiles = getFtpFile(ftp, filepath);
        List<FTPFile> ftpFileList = filterDownloadedFtpFiles(allFtpFiles, fileExtension);
        List<FTPFile> alreadyDownloadedFiles = allFtpFiles.stream().filter(f -> !ftpFileList.contains(f)).collect(toList());
        ftp.delete(filepath, alreadyDownloadedFiles);
    }

    private List<FTPFile> getFtpFile(FtpUtil ftp, String filepath) throws IOException {
        ftp.changeWorkingDirectory(filepath);
        ftp.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftp.listFiles();
        return null == ftpFiles ? Lists.newArrayList() : Arrays.stream(ftpFiles).collect(toList());
    }

    private List<FTPFile> filterDownloadedFtpFiles(List<FTPFile> ftpFiles, String fileExtension) {
        // 过滤掉非法文件
        Map<String, Long> downloadedFiles = getDownloadedFiles();
        Predicate<FTPFile> ftpFilePredicate =
                f -> !( downloadedFiles.containsKey( f.getName() ) && downloadedFiles.get( f.getName() ).equals( f.getSize() ) ) &&
                        fileExtension.equalsIgnoreCase( FilenameUtils.getExtension( f.getName() ) );

        return ftpFiles.stream().filter(ftpFilePredicate).collect(toList());
    }

    private Map<String, Long> getDownloadedFiles() {
        String key = EnumRedisKey.PAAS_DOWLOADED_FILES.getKey();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        return entries.entrySet().stream().collect(toMap(x -> (String) x.getKey(), y -> (Long) y.getValue()));
    }

    private void setDowloadedFile(String filename, Long size) {
        String key = EnumRedisKey.PAAS_DOWLOADED_FILES.getKey();
        redisTemplate.opsForHash().put(key, filename, size);
        redisTemplate.expire(key, 3, TimeUnit.DAYS);
    }
}
