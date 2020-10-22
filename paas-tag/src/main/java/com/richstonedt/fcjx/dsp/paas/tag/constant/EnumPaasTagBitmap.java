package com.richstonedt.fcjx.dsp.paas.tag.constant;

/**
 * <b><code>EnumPaasTagBitmap</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/17 2:59 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public enum EnumPaasTagBitmap {

    /**
     * 有效标签
     */
    STATUS_ON(1),
    /**
     * 无效标签
     */
    STATUS_OFF(0);

    private int value;

    EnumPaasTagBitmap(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
