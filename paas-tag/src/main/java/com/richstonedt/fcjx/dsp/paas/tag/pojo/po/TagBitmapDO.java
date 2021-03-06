package com.richstonedt.fcjx.dsp.paas.tag.pojo.po;

import lombok.*;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table tag_bitmaps
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagBitmapDO {
    /**
     * Database Column Remarks:
     *   标签名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.tag
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private String tag;

    /**
     * Database Column Remarks:
     *   状态：0-已失效，1-有效
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Date createdTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private Date updatedTime;

    /**
     * Database Column Remarks:
     *   备注
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private String remark;

    /**
     * Database Column Remarks:
     *   序列化后的bitmap对象
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag_bitmaps.bitmap_object
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    private byte[] bitmapObject;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.tag
     *
     * @return the value of tag_bitmaps.tag
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.tag
     *
     * @param tag the value for tag_bitmaps.tag
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.status
     *
     * @return the value of tag_bitmaps.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.status
     *
     * @param status the value for tag_bitmaps.status
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.created_time
     *
     * @return the value of tag_bitmaps.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.created_time
     *
     * @param createdTime the value for tag_bitmaps.created_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.updated_time
     *
     * @return the value of tag_bitmaps.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.updated_time
     *
     * @param updatedTime the value for tag_bitmaps.updated_time
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.remark
     *
     * @return the value of tag_bitmaps.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.remark
     *
     * @param remark the value for tag_bitmaps.remark
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag_bitmaps.bitmap_object
     *
     * @return the value of tag_bitmaps.bitmap_object
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public byte[] getBitmapObject() {
        return bitmapObject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag_bitmaps.bitmap_object
     *
     * @param bitmapObject the value for tag_bitmaps.bitmap_object
     *
     * @mbg.generated Thu Apr 16 15:18:18 CST 2020
     */
    public void setBitmapObject(byte[] bitmapObject) {
        this.bitmapObject = bitmapObject;
    }
}