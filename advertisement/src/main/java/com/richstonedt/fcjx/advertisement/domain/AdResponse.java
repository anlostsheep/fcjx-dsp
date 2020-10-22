package com.richstonedt.fcjx.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>AdResponse</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 11:57.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdResponse implements Serializable {

    private static final long serialVersionUID = 6210964660522695828L;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("ads")
    private List<Ad> ads;
}
