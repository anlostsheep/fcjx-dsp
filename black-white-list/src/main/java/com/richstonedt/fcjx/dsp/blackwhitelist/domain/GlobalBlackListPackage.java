package com.richstonedt.fcjx.dsp.blackwhitelist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>AllBlackListPackage</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 11:37.
 *
 * @author user
 * @since dsp_blackwhitelist
 */

/**
 * 全局黑名单POJO
 * @author user
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalBlackListPackage implements Serializable {
     /**
      * 全局黑名单的号码列表
      */
     private List<String> phoneNums;

     /**
      * 状态
      */
     private Byte status;
}
