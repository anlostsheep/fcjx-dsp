package com.richstonedt.fcjx.advertisement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>App</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 20:02.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class App implements Serializable {
    private static final long serialVersionUID = 5411314147619899439L;

    @JsonProperty("app_id")
    @JSONField(name = "app_id")
    private String appId;

    @JsonProperty("app_category")
    @JSONField(name = "app_category")
    private String appCategory;

    @JsonProperty("app_bundle_id")
    @JSONField(name = "app_bundle_id")
    private String appBundleId;

    @JsonProperty("action_type")
    @JSONField(name = "action_type")
    private String actionType;
}
