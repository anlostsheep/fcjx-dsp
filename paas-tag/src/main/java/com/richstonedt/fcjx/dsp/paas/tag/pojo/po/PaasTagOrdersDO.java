package com.richstonedt.fcjx.dsp.paas.tag.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table paas_tag_orders
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaasTagOrdersDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.id
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.create_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   工单号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.order_no
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private String orderNo;

    /**
     * Database Column Remarks:
     *   paas平台用户标签
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.paas_tag
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private String paasTag;

    /**
     * Database Column Remarks:
     *   备注
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.remark
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private String remark;

    /**
     * Database Column Remarks:
     *   状态：0-已失效，1-有效
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.status
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_orders.update_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.id
     *
     * @return the value of paas_tag_orders.id
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.id
     *
     * @param id the value for paas_tag_orders.id
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.create_time
     *
     * @return the value of paas_tag_orders.create_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.create_time
     *
     * @param createTime the value for paas_tag_orders.create_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.order_no
     *
     * @return the value of paas_tag_orders.order_no
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.order_no
     *
     * @param orderNo the value for paas_tag_orders.order_no
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.paas_tag
     *
     * @return the value of paas_tag_orders.paas_tag
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public String getPaasTag() {
        return paasTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.paas_tag
     *
     * @param paasTag the value for paas_tag_orders.paas_tag
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setPaasTag(String paasTag) {
        this.paasTag = paasTag == null ? null : paasTag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.remark
     *
     * @return the value of paas_tag_orders.remark
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.remark
     *
     * @param remark the value for paas_tag_orders.remark
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.status
     *
     * @return the value of paas_tag_orders.status
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.status
     *
     * @param status the value for paas_tag_orders.status
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_orders.update_time
     *
     * @return the value of paas_tag_orders.update_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_orders.update_time
     *
     * @param updateTime the value for paas_tag_orders.update_time
     *
     * @mbg.generated Mon Apr 13 14:44:12 CST 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}