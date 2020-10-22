package com.richstonedt.fcjx.dsp.paas.tag.service.impl;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.common.util.CipherUtil;
import com.richstonedt.fcjx.dsp.paas.tag.constant.EnumPaasTag;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagPageableBO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;
import com.richstonedt.fcjx.dsp.paas.tag.repository.PaasTagRepository;
import com.richstonedt.fcjx.dsp.paas.tag.repository.TagBitmapRepository;
import com.richstonedt.fcjx.dsp.paas.tag.service.IPaasTagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.roaringbitmap.longlong.LongIterator;
import org.roaringbitmap.longlong.Roaring64NavigableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>PaasTagServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 3:48 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Service
@Slf4j
@RefreshScope
public class PaasTagServiceImpl implements IPaasTagService {

    @Value("${paas.tag.secret}")
    private String secret;

    private final PaasTagRepository paasTagRepository;
    private final TagBitmapRepository tagBitmapRepository;

    public PaasTagServiceImpl(PaasTagRepository paasTagRepository, TagBitmapRepository tagBitmapRepository) {
        this.paasTagRepository = paasTagRepository;
        this.tagBitmapRepository = tagBitmapRepository;
    }

    @Override
    public List<PaasTagDTO> getTagByPhone(String phone) {
        long phoneL = Long.parseLong(phone);
        byte status = (byte) EnumPaasTag.STATUS_ON.getValue();
        List<PaasTagDTO> tagDTOList = paasTagRepository.findTagByStatus(status);
        return tagDTOList.stream()
                .filter(dto -> tagBitmapRepository.containsInBitmap(dto.getName(), phoneL))
                .collect(toList());
    }

    @Override
    public PaasTagPageableBO<String> getPhoneByTagAllIn(List<String> tags, final long pageNum, final long pageSize) {
        Roaring64NavigableMap resultBitmap = bitmapOperation(tags, Roaring64NavigableMap::and);
        List<String> phones = bitmapPageable(resultBitmap, pageNum, pageSize);
        return PaasTagPageableBO.<String>builder()
                .data(phones)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .count(resultBitmap.getLongCardinality())
                .build();
    }

    @Override
    public PaasTagPageableBO<String> getPhoneByTagAnyIn(List<String> tags, long pageNum, long pageSize) {
        Roaring64NavigableMap resultBitmap = bitmapOperation(tags, Roaring64NavigableMap::or);
        List<String> phones = bitmapPageable(resultBitmap, pageNum, pageSize);
        return PaasTagPageableBO.<String>builder()
                .data(phones)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .count(resultBitmap.getLongCardinality())
                .build();
    }

    /**
     * bitmap简单分页 //TODO 待优化，后面页数非常慢
     * @param bitmap
     * @param pageNum
     * @param pageSize
     * @return
     */
    private List<String> bitmapPageable(Roaring64NavigableMap bitmap, long pageNum, long pageSize) {
        List<String> result = Lists.newArrayList();
        long previous = pageSize * (pageNum - 1);
        long longCardinality = bitmap.getLongCardinality();

        if (previous >= longCardinality) {
            return result;
        }

        long j = 0;
        LongIterator i = bitmap.getLongIterator();

        while (i.hasNext() && j++ < previous) {
            i.next();
        }

        for(int k = 0; (k < pageSize) && i.hasNext(); k++) {
            result.add( String.valueOf( i.next() ) );
        }

        return result;
    }

    /**
     * bitmap位运算
     * @param tags
     * @param biConsumer
     */
    private Roaring64NavigableMap bitmapOperation(List<String> tags, BiConsumer<Roaring64NavigableMap, Roaring64NavigableMap> biConsumer) {
        Roaring64NavigableMap resultBitmap = new Roaring64NavigableMap();
        List<Roaring64NavigableMap> bitmaps = tags.stream().map(tagBitmapRepository::findBitmapByTagName).collect(toList());

        for (int i = 0; i < bitmaps.size(); i++) {
            Roaring64NavigableMap bitmap = bitmaps.get(i);
            if (0 == i) {
                resultBitmap.or(bitmap);
            } else {
                biConsumer.accept(resultBitmap, bitmap);
            }
        }

        return resultBitmap;
    }

    @Override
    public Optional<String> encrypt(String phone) {
        try {
            String encrypt = CipherUtil.aesEncrypt(phone, secret);
            return Optional.of(encrypt);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            log.error("号码加密失败：{}", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> decrypt(String phone) {
        try {
            String decrypt = CipherUtil.aesDecrypt(phone, secret);
            return Optional.of(decrypt);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | DecoderException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            log.error("号码解密失败：{}", e);
        }

        return Optional.empty();
    }
}
