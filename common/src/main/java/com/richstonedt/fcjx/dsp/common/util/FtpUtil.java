package com.richstonedt.fcjx.dsp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Slf4j
public class FtpUtil implements Closeable {

    private FTPClient ftp;

    private FtpUtil(String ftpHost, int ftpPort, String ftpUsername, String ftpPassword) throws IOException {
        log.info("登录ftp");
        ftp = new FTPClient();
        ftp.setRemoteVerificationEnabled(false);
        ftp.connect(ftpHost, ftpPort);
        ftp.enterLocalPassiveMode();
        boolean isLogin = ftp.login(ftpUsername, ftpPassword);
        Assert.isTrue(isLogin, "ftp登录失败");
        log.info("已登录ftp");
    }

    public static FtpUtil getClient(String ftpHost, int ftpPort, String ftpUsername, String ftpPassword) throws IOException {
        return new FtpUtil(ftpHost, ftpPort, ftpUsername, ftpPassword);
    }

    public void changeWorkingDirectory(String path) throws IOException {
        ftp.changeWorkingDirectory(path);
    }

    public void enterLocalPassiveMode() {
        ftp.enterLocalPassiveMode();
    }

    public FTPFile[] listFiles() throws IOException {
        return ftp.listFiles();
    }

    public void download(List<FTPFile> ftpFileList) {
        log.info("下载数据");
        ftpFileList.forEach(f -> {
            String fName = f.getName();
            log.info("ftp文件:{}", fName);
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fName), 10 * 1024 * 1024)) {
                ftp.retrieveFile(fName, outputStream);
            } catch (IOException e) {
                log.error("读取FTP文件失败:{}", e.getMessage());
            }
        });
    }

    public void download(String filepath) throws IOException {
        log.info("下载数据");
        ftp.changeWorkingDirectory(filepath);
        ftp.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftp.listFiles();
        Assert.isTrue(ftpFiles != null && ftpFiles.length > 0, "ftp文件夹为空");
        List<FTPFile> ftpFileList = Arrays.stream(ftpFiles).collect(toList());
        download(ftpFileList);
    }

    public void rename(String filepath, Function<String, String> fn, Predicate<FTPFile> predicate) throws IOException {
        ftp.changeWorkingDirectory(filepath);
        ftp.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftp.listFiles();
        List<FTPFile> collect = Arrays.stream(ftpFiles).filter(predicate).collect(toList());
        for (FTPFile f : collect) {
            ftp.rename(f.getName(), fn.apply(f.getName()));
        }
    }

    public void delete(String filepath, List<FTPFile> ftpFileList) throws IOException {
        ftp.changeWorkingDirectory(filepath);
        for (FTPFile f : ftpFileList) {
            ftp.deleteFile(f.getName());
        }
    }

    @Override
    public void close() throws IOException {
        ftp.disconnect();
        log.info("断开ftp连接");
    }
}
