package com.richstonedt.fcjx.advertisement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * <b><code>ComplaintList</code></b>
 * <p/>
 * 投诉名单列表
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 16:16.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintList {

    private List<String> phoneNums;
}
