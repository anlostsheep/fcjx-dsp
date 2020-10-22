package com.richstonedt.fcjx.dsp.blackwhitelist.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>BlackListRegionEntity</code></b>
 * <p/>
 * 黑白名单实体
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:12.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
@Table(name = "black_white_lists")
public class BlackWhiteListEntity implements Serializable {

    /**
     * 一般是由广告素材id+地区组成+名单类型(黑或白)
     */
    @Id
    @Column(columnDefinition = "CHAR(32) COMMENT '主键( 由广告素材id+地区组成+名单类型(黑或白)HashCode组成 )'")
    private String id;
    @Column(name="advertisement_id", columnDefinition = "CHAR(32) COMMENT '广告素材ID'")
    private String adId;
    @Column(columnDefinition = "CHAR(16) COMMENT '地市缩写'")
    private String region;
    /**
     * 0 -> 白名单， 1 -> 黑名单， 2-全量投放
      */
    @Column(columnDefinition = "TINYINT COMMENT '名单类型：0-白名单，1-黑名单，2-全量投放'")
    private Byte type;
    /**
     * 黑白名单标签，用于区分不同广告素材黑白名单以及全局黑白名单
     */
    @Column(columnDefinition = "CHAR(64) COMMENT '名单标签(用于区分不同广告素材黑白名单以及全局黑白名单)'")
    private String tag;
    @Column(columnDefinition = "TINYINT COMMENT '状态：0-已失效，1-有效'")
    private Byte status;
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
    @Column(name="created_time", columnDefinition = "DATETIME COMMENT '创建时间'")
    private Date createAt;

    @Override
    public String toString() {
        return "BlackWhiteListEntity{" +
                "id='" + id + '\'' +
                ", adId='" + adId + '\'' +
                ", region='" + region + '\'' +
                ", type=" + type +
                ", tag='" + tag + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlackWhiteListEntity)) {
            return false;
        }
        BlackWhiteListEntity that = (BlackWhiteListEntity) o;
        return getAdId().equals(that.getAdId()) &&
                getRegion().equals(that.getRegion()) &&
                getType().equals(that.getType());
    }

    public String primary() {
        return adId + region + type;
    }
}
