package com.richstonedt.fcjx.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b><code>TagVo</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/5/19 16:05.
 *
 * @author dengzhen
 * @since fcjx-dsp 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagVo {
    
    private String name;
    
    private String alias;
    
    private Integer status;
}
