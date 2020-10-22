package com.richstonedt.fcjx.dsp.paas.tag.pojo.assember;

import com.richstonedt.fcjx.dsp.paas.tag.constant.EnumPaasTagBitmap;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.po.TagBitmapDO;

import java.util.Date;

/**
 * <b><code>PaasTagBitmapAssember</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/17 3:00 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public class PaasTagBitmapAssember {

    public static TagBitmapDO build(String tagName, byte[] bytes) {
        return TagBitmapDO.builder()
                .tag(tagName)
                .bitmapObject(bytes)
                .createdTime(new Date())
                .updatedTime(new Date())
                .remark("")
                .status((byte) EnumPaasTagBitmap.STATUS_ON.getValue())
                .build();
    }
}
