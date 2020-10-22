package com.richstonedt.fcjx.dsp.blackwhitelist.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>BlackWhiteListEntity</code></b>
 * <p/>
 * 号码列表实体
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:13.
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
@Table(name = "phone_lists")
public class PhoneListEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 号码标签
     * 根据广告素材的不同，
     * 以及地区的不同，
     * 以及名单类型的不同（黑或白或全局黑），
     * 对应这个不同的标签
     */
    @Column(columnDefinition = "CHAR(64) COMMENT '号码标签(根据广告素材的不同，以及地区的不同，以及名单类型的不同（黑或白或全局黑），对应这个不同的标签)'")
    private String tag;
    @Column(columnDefinition = "CHAR(16) COMMENT '号码'")
    private String phoneNum;
    @Column(columnDefinition = "VARCHAR(255) COMMENT '下载号码包URL'")
    private String url;
    /**
     * 表示号码是否已经移除出某广告素材所对应名单
     * 仅用于逻辑删除
     */
    @Column(columnDefinition = "TINYINT COMMENT '状态：0-号码已失效，1-号码有效'")
    private Byte status;
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注'")
    private String remark;
    @Column(name="created_time", columnDefinition = "DATETIME COMMENT '创建时间'")
    private Date createAt;
    @Column(name="updated_time", columnDefinition = "DATETIME COMMENT '更新时间'")
    private Date updateAt;

    @Override
    public String toString() {
        return "PhoneListEntity{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
