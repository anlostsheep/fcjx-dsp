package com.richstonedt.fcjx.advertisement.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>User</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 20:15.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1756673269072757707L;

    @JsonProperty("gender")
    @JSONField(name = "gender")
    private String gender;

    @JsonProperty("ip")
    @JSONField(name = "ip")
    private String ip;

    @JsonProperty("user_id")
    @JSONField(name = "user_id")
    private String userId;

    @JsonProperty("latitude")
    @JSONField(name = "latitude")
    private String latitude;

    @JsonProperty("longtitude")
    @JSONField(name = "longtitude")
    private String longitude;

    @JsonProperty("phone_number")
    @JSONField(name = "phone_number")
    private String phoneNumber;
}
