package com.richstonedt.fcjx.dsp.paas.tag.constant;

/**
 * <b><code>EnumPaasTag</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 4:07 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public enum EnumPaasTag {

    /**
     * 有效标签
     */
    STATUS_ON(1),
    /**
     * 无效标签
     */
    STATUS_OFF(0);

    private int value;

    EnumPaasTag(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
