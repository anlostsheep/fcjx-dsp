package com.richstonedt.fcjx.advertisement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b><code>Advertisement</code></b>
 * <p/>
 * 广告请求参数实体
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 19:59.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class Advertisement implements Serializable {
    private static final long serialVersionUID = 516849878257958328L;

    @JsonProperty("sequence_id")
    @JSONField(name = "sequence_id")
    private String sequenceId;

    @ApiModelProperty(value = "广告位宽度")
    @NotNull(message = "广告位宽度不能为空")
    @JsonProperty("width")
    @JSONField(name = "width")
    private Integer width;

    @ApiModelProperty(value = "广告位高度")
    @NotNull(message = "广告位高度不能为空")
    @JsonProperty("height")
    @JSONField(name = "height")
    private Integer height;

    @JsonProperty("adslot_type")
    @JSONField(name = "adslot_type")
    private String adsLotType;

    @JsonProperty("user_agent")
    @JSONField(name = "user_agent")
    private String userAgent;

    @JsonProperty("referer")
    @JSONField(name = "referer")
    private String referer;

    @JsonProperty("url")
    @JSONField(name = "url")
    private String url;

    @JsonProperty("ad_count")
    @JSONField(name = "ad_count")
    private Integer adCount;

    @JsonProperty("ad_type")
    @JSONField(name = "ad_type")
    private Integer adType;

    @JsonProperty("match")
    @JSONField(name = "match")
    private String match;

}
