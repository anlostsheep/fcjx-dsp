package com.richstonedt.fcjx.advertisement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <b><code>Device</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 20:11.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device implements Serializable {
    private static final long serialVersionUID = -4138824548291332082L;

    @JsonProperty("brand")
    @JSONField(name = "brand")
    private String brand;

    @JsonProperty("os")
    @JSONField(name = "os")
    private String os;

    @JsonProperty("os_version")
    @JSONField(name = "os_version")
    private String osVersion;

    @JsonProperty("screen_width")
    @JSONField(name = "screen_width")
    private String screenWidth;

    @JsonProperty("screen_height")
    @JSONField(name = "screen_height")
    private String screenHeight;

    @JsonProperty("network_type")
    @JSONField(name = "network_type")
    private String networkType;

    @JsonProperty("carrier_id")
    @JSONField(name = "carrier_id")
    private String carrierId;

    @NotBlank(message = "手机号码不能为空")
    @Min(value = 11, message = "手机号码非法")
    @JsonProperty("msisdn")
    @JSONField(name = "msisdn")
    private String phoneNum;

    @JsonProperty("imei")
    @JSONField(name = "imei")
    private String iMei;

    @JsonProperty("idfa")
    @JSONField(name = "idfa")
    private String idFa;

    @JsonProperty("mac")
    @JSONField(name = "mac")
    private String mac;

    @JsonProperty("openudid")
    @JSONField(name = "openudid")
    private String openUdId;

    @JsonProperty("region")
    @JSONField(name = "region")
    private String region;
}
