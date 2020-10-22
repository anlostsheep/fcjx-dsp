package com.richstonedt.fcjx.dsp.blackwhitelist.constant;

/**
 * <b><code>Constant</code></b>
 * <p/>
 * 常量定义
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 11:07.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
public class Constant {

    public static final class PhonePackage {
        /**
         * 广告素材白名单上传操作类型字段
         */
        public static final String OP_TYPE_ADD_WHITE_LIST = "A";
        /**
         * 广告素材黑名单上传操作类型字段
         */
        public static final String OP_TYPE_ADD_BLACK_LIST = "ABL";
        /**
         * 广告素材全量投放操作类型字段
         */
        public static final String OP_TYPE_ALL_LIST = "NPA";
    }

    public static final class Ad {
        /**
         * 记录是否有效标志位，0-广告素材已下线，1-广告素材在线
         */
        public static final byte STATUS_OFF = 0;
        public static final byte STATUS_ON = 1;
    }

    public static final class BlackWhiteList {
        /**
         * 记录是否有效标志位，0-已失效或已删除，1-有效
         */
        public static final byte STATUS_OFF = 0;
        public static final byte STATUS_ON = 1;
        public static final byte TYPE_WHITE_LIST = 0;
        public static final byte TYPE_BLACK_LIST = 1;
        /**
         * 全量投放类型，全量投放的意思是，所有人都看得到
         */
        public static final byte TYPE_ALL = 2;
        /**
         * 全局黑名单标签值
         */
        public static final String GLOBAL_BLACK_LIST_TAG = "GLOBAL_BLACK_LIST_TAG";
    }

    public static final class PhoneList {
        /**
         * 记录是否有效标志位，0-已失效或已删除，1-有效
         */
        public static final byte STATUS_OFF = 0;
        public static final byte STATUS_ON = 1;
    }
}
