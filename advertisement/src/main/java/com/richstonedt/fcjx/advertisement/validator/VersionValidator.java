package com.richstonedt.fcjx.advertisement.validator;

import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b><code>VersionValidator</code></b>
 * <p/>
 * 版本号注解校验类
 * <p/>
 * <b>Creation Time:</b> 2020/5/7 2:00.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
public class VersionValidator implements ConstraintValidator<VersionValid, String> {
    @Override
    public void initialize(VersionValid constraintAnnotation) {
        log.info("初始化 version 版本校验注解");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ApiContants.VERSION_V20.equals(value);
    }
}
