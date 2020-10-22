package com.richstonedt.fcjx.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>Ad</code></b>
 * <p/>
 * 广告数据
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 13:06.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ad implements Serializable {

    private static final long serialVersionUID = 8173284672767146340L;

    @JsonProperty("adid")
    private String adId;

    @JsonProperty("paymode")
    private Integer payMode;

    @JsonProperty("adtype")
    private String adType;

    @JsonProperty("html_snippet")
    private String htmlSnippet;

    @JsonProperty("adwidth")
    private Integer adWidth;

    @JsonProperty("adheight")
    private Integer adHeight;

    @JsonProperty("imgurl")
    private String imgUrl;

    @JsonProperty("adtitle")
    private String adTitle;

    @JsonProperty("adcontent")
    private String adContent;

    @JsonProperty("adurl")
    private String adUrl;

    @JsonProperty("showurl")
    private String showUrl;

    @JsonProperty("clickurl")
    private String clickUrl;

    @JsonProperty("orderid")
    private String orderId;
}
