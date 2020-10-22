package com.richstonedt.fcjx.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <b><code>AdTagDto</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/5/19 16:04.
 *
 * @author dengzhen
 * @since fcjx-dsp 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdTagDto {
    
    private Integer code;
    
    private String desc;
    
    private List<TagVo> data;
}
