package com.richstonedt.fcjx.advertisement.dao;

import com.richstonedt.fcjx.advertisement.domain.MobileLocal;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>MobileLocalMapper</code></b>
 * <p/>
 * 手机号码查找归属地信息 mapper
 * <p/>
 * <b>Creation Time:</b> 2020/4/2 15:32.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Mapper
@Repository
public interface MobileLocalMapper {

    /**
     * 根据号码段查询归属地
     *
     * @param mobileSegment 号码段(手机号码前7位)
     * @return 号码段信息
     */
    @Select("select * from smartpush_mobile_map_info where tel_num=#{mobileSegment}")
    @Results(id = "mobileSegmentMap", value = {
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(column = "tel_num", property = "numberSegment", javaType = String.class),
            @Result(column = "area", property = "area", javaType = String.class),
            @Result(column = "area_alias", property = "areaAlias", javaType = String.class),
            @Result(column = "tel_type", property = "numberType", javaType = String.class),
            @Result(column = "area_code", property = "areaCode", javaType = String.class),
            @Result(column = "post_code", property = "postCode", javaType = String.class)
    })
    MobileLocal findByPhoneNumber(@Param("mobileSegment") String mobileSegment);

    /**
     * 根据地市名称匹配号码段
     *
     * @param area 地市
     * @return 号码段信息
     */
    @ResultMap("mobileSegmentMap")
    @Select("select * from smartpush_mobile_map_info where area=#{area}")
    List<MobileLocal> findByArea(@Param("area") String area);

    /**
     * 查询所有的地市号码段
     *
     * @return 号码段/地市信息
     */
    @Select("select tel_num, area_alias from smartpush_mobile_map_info")
    @ResultMap("mobileSegmentMap")
    List<MobileLocal> findAll();
}
