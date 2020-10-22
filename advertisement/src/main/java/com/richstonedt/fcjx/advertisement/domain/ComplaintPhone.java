package com.richstonedt.fcjx.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <b><code>ComplaintList</code></b>
 * <p/>
 * 投诉号码
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 15:50.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintPhone {

    @JsonProperty(value = "phone")
    @NotBlank(message = "投诉名单号码不能为空")
    @Min(value = 11, message = "投诉名单号码校验不通过")
    private String phoneNumber;
}
