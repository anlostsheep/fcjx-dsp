package com.richstonedt.fcjx.advertisement.validator;

import com.richstonedt.fcjx.advertisement.bean.ChannelMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <b><code>ChannelValidator</code></b>
 * <p/>
 * 自定义注解校验类
 * <p/>
 * <b>Creation Time:</b> 2020/4/2 17:27.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
public class ChannelValidator implements ConstraintValidator<ChannelValid, String> {

    private final ChannelMap channelMap;

    @Autowired
    public ChannelValidator(ChannelMap channelMap) {
        this.channelMap = channelMap;
    }

    @Override
    public void initialize(ChannelValid channelValid) {
        log.info("初始化检验注解");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.isEmpty(value) && channelMap.names.containsKey(value);
    }
}
