package com.richstonedt.fcjx.advertisement.utils;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * <b><code>PhonePackageUtils</code></b>
 * <p/>
 * 号码包(URL)操作工具类
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 15:03.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component
public class PhonePackageUtils {

    public List<String> readFromUrl(String phonePackageUrl) {
        List<String> list = Lists.newArrayList();

        try {
            URL url = new URL(phonePackageUrl);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            log.error("读取号码包地址:{}出现异常", phonePackageUrl, e);
            throw new SmartPushException(ApiEnum.INTERNAL_ERROR);
        }

        return list;
    }
}
