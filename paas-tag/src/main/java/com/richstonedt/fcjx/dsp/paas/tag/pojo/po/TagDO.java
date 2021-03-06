package com.richstonedt.fcjx.dsp.paas.tag.pojo.po;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tags
 */
public class TagDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.id
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   标签名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.name
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private String name;

    /**
     * Database Column Remarks:
     *   标签别名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.alias
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private String alias;

    /**
     * Database Column Remarks:
     *   状态：0-已失效，1-有效
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Date createdTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Date updatedTime;

    /**
     * Database Column Remarks:
     *   备注
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tags.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.id
     *
     * @return the value of tags.id
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.id
     *
     * @param id the value for tags.id
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.name
     *
     * @return the value of tags.name
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.name
     *
     * @param name the value for tags.name
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.alias
     *
     * @return the value of tags.alias
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.alias
     *
     * @param alias the value for tags.alias
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.status
     *
     * @return the value of tags.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.status
     *
     * @param status the value for tags.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.created_time
     *
     * @return the value of tags.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.created_time
     *
     * @param createdTime the value for tags.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.updated_time
     *
     * @return the value of tags.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.updated_time
     *
     * @param updatedTime the value for tags.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tags.remark
     *
     * @return the value of tags.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tags.remark
     *
     * @param remark the value for tags.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}