package com.richstonedt.fcjx.dsp.paas.tag.service;

import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagPageableBO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;

import java.util.List;
import java.util.Optional;

/**
 * <b><code>IPaasTagService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 3:45 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public interface IPaasTagService {

    /**
     * 通过号码获取标签
     * @param phone
     * @return
     */
    List<PaasTagDTO> getTagByPhone(String phone);

    /**
     * 通过标签获取号码（标签交集）
     * @param tags
     * @param pageNum
     * @param pageSize
     * @return
     */
    PaasTagPageableBO<String> getPhoneByTagAllIn(List<String> tags, long pageNum, long pageSize);

    /**
     * 通过标签获取号码（标签并集）
     * @param tags
     * @param pageNum
     * @param pageSize
     * @return
     */
    PaasTagPageableBO<String> getPhoneByTagAnyIn(List<String> tags, long pageNum, long pageSize);

    /**
     * 号码加密
     * @param phone
     * @return
     */
    Optional<String> encrypt(String phone);

    /**
     * 号码解密
     * @param phone
     * @return
     */
    Optional<String> decrypt(String phone);
}
