package com.richstonedt.fcjx.advertisement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.richstonedt.fcjx.advertisement.validator.VersionValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <b><code>AdRequest</code></b>
 * <p/>
 * 广告请求
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 19:47.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "广告请求信息", description = "判断请求的广告类型数量等")
public class AdRequest implements Serializable {
    private static final long serialVersionUID = 3667864305260314864L;

    @ApiModelProperty(value = "请求 id")
    @NotBlank(message = "请求 Id 不能为空")
    @JsonProperty("request_id")
    @JSONField(name = "request_id")
    private String requestId;

    @JsonProperty("channel_id")
    @JSONField(name = "channel_id")
    private String channelId;

    @ApiModelProperty(value = "广告位参数")
    @Valid
    @NotNull(message = "广告位参数不能为空")
    @JsonProperty("adslot")
    @JSONField(name = "adslot")
    private Advertisement ad;

    @JsonProperty("app")
    @JSONField(name = "app")
    private App app;

    @Valid
    @ApiModelProperty(value = "请求设备信息")
    @NotNull(message = "设备参数不能为空")
    @JsonProperty("device")
    private Device device;


    @JsonProperty("user")
    private User user;

    @NotBlank(message = "版本号不能为空")
    @VersionValid(message = "已不再支持的版本,请使用 V20 版本号")
    @JsonProperty("version")
    @JSONField(name = "version")
    private String version;

    @JsonProperty("adid")
    @JSONField(name = "adid")
    private String adId;

    @JsonProperty("adids")
    @JSONField(name = "adids")
    private List<String> adIds;

    @JsonProperty("flow_type")
    @JSONField(name = "flow_type")
    private String flowType;

}
