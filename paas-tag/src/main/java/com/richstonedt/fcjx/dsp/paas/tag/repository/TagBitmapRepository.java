package com.richstonedt.fcjx.dsp.paas.tag.repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.richstonedt.fcjx.dsp.paas.tag.dao.TagBitmapDOMapper;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.assember.PaasTagBitmapAssember;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.po.TagBitmapDO;
import lombok.extern.slf4j.Slf4j;
import org.roaringbitmap.longlong.Roaring64NavigableMap;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * <b><code>TagBitmapRepository</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/17 2:54 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Repository
@Slf4j
public class TagBitmapRepository {

    private final LoadingCache<String, Roaring64NavigableMap> bitmapCache;
    private final TagBitmapDOMapper tagBitmapDOMapper;

    public TagBitmapRepository(TagBitmapDOMapper tagBitmapDOMapper) {
        this.tagBitmapDOMapper = tagBitmapDOMapper;
        this.bitmapCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .softValues()
                .build(new CacheLoader<String, Roaring64NavigableMap>() {
                    @Override
                    public Roaring64NavigableMap load(String tagName) {
                        return loadBitmap(tagName);
                    }
                });
    }

    public boolean containsInBitmap(String tagName, long value) {
        Roaring64NavigableMap bitmap = findBitmapByTagName(tagName);
        return bitmap.contains(value);
    }

    public void putBitmap(String tagName, long value) {
        Roaring64NavigableMap bitmap = findBitmapByTagName(tagName);
        bitmap.add(value);
    }

    public Roaring64NavigableMap findBitmapByTagName(String tagName) {
        try {
            return bitmapCache.get(tagName);
        } catch (ExecutionException e) {
            log.error("获取bitmap cache失败：{}", e);
        }
        return null;
    }

    public void saveBitmap(String tagName) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(tagName); ) {
            Roaring64NavigableMap bitmap = findBitmapByTagName(tagName);
            bitmap.runOptimize();
            bitmap.serialize(dataOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            TagBitmapDO tagBitmapDO = PaasTagBitmapAssember.build(tagName, bytes);
            upsert(tagBitmapDO);

            fileOutputStream.write(bytes);
        } catch (IOException e) {
            log.info("持久化bitmap失败：{}", e);
        }
    }

    private Roaring64NavigableMap loadBitmap(String tagName) {
        File file = new File(tagName);
        Roaring64NavigableMap bitmap;
        try {
            bitmap = file.exists() ? loadBitmapFromFile(file) : loadBitmapFromDb(file, tagName);
        } catch (IOException e) {
            log.info("反序列化bitmap失败：{}", e);
            log.info("删除本地bitmap文件{} ==> {}", file.getName(), file.delete());
            bitmap = loadBitmapFromDb(file, tagName);
        }

        return bitmap;
    }

    private Roaring64NavigableMap loadBitmapFromFile(File file) throws IOException {
        Assert.isTrue(file.exists(), "bitmap文件本地不存在");
        Roaring64NavigableMap bitmap = new Roaring64NavigableMap();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             DataInputStream in = new DataInputStream(bufferedInputStream)) {
            bitmap.deserialize(in);
        }

        return bitmap;
    }

    /**
     * 重新从数据库下载bitmap到本地反序列化
     * @param file
     * @param tagName
     * @return
     */
    private Roaring64NavigableMap loadBitmapFromDb(File file, String tagName) {
        Roaring64NavigableMap bitmap = new Roaring64NavigableMap();
        Optional<TagBitmapDO> bitmapDO = findByTagName(tagName);
        bitmapDO.ifPresent(bmDO -> {
            byte[] bytes = bmDO.getBitmapObject();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                 DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream)) {
                bitmap.deserialize(dataInputStream);
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                log.info("持久化bitmap到本地文件失败：{}", e);
            }
        });

        return bitmap;
    }

    public int insert(TagBitmapDO bitmapDO) {
        return tagBitmapDOMapper.insert(bitmapDO);
    }

    public Optional<TagBitmapDO> findByTagName(String tagName) {
        TagBitmapDO tagBitmapDO = tagBitmapDOMapper.selectByPrimaryKey(tagName);
        return Optional.ofNullable(tagBitmapDO);
    }

    public void upsert(TagBitmapDO bitmapDO) {
        tagBitmapDOMapper.upsert(bitmapDO);
    }
}
