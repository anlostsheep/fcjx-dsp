package com.richstonedt.fcjx.dsp.blackwhitelist.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>BlackWhiteListStatusEntity</code></b>
 * <p/>
 * 广告素材实体
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
@Table(name = "advertisements")
public class AdEntity implements Serializable {

    /**
     * 一般是由广告素材id+地区组成+渠道组成
     */
    @Id
    @Column(columnDefinition = "CHAR(32) COMMENT '主键( 由广告素材id+地区组成+名单类型(黑或白)HashCode组成 )'")
    private String id;
    @Column(name="advertisement_id", columnDefinition = "CHAR(32) COMMENT '广告素材ID'")
    private String adId;
    @Column(columnDefinition = "CHAR(16) COMMENT '地市缩写'")
    private String region;
    @Column(columnDefinition = "CHAR(16) COMMENT '渠道'")
    private String platform;
    @Column(columnDefinition = "TINYINT COMMENT '状态：0-广告已下线，1-广告在线'")
    private Byte status;
    @Column(name="created_time", columnDefinition = "DATETIME COMMENT '创建时间'")
    private Date createAt;
    @Column(name="updated_time", columnDefinition = "DATETIME COMMENT '更新时间'")
    private Date updateAt;
    /**
     * 广告素材类型
     * 与全量素材什么什么之类是不一样的
     * 该字段待用
     */
    @Column(columnDefinition = "TINYINT COMMENT '广告素材类型(与全量素材什么什么之类是不一样的，该字段为预留字段)'")
    private Byte type;
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;

    @Override
    public String toString() {
        return "AdEntity{" +
                "id='" + id + '\'' +
                ", adId='" + adId + '\'' +
                ", region='" + region + '\'' +
                ", platform='" + platform + '\'' +
                ", status=" + status +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdEntity)) {
            return false;
        }
        AdEntity adEntity = (AdEntity) o;
        return getAdId().equals(adEntity.getAdId()) &&
                getRegion().equals(adEntity.getRegion()) &&
                getPlatform().equals(adEntity.getPlatform());
    }

    public String primary() {
        return adId + region + platform;
    }
}
