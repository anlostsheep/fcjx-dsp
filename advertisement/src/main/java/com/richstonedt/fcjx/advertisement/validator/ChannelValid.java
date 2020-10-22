package com.richstonedt.fcjx.advertisement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b><code>ChannelValid</code></b>
 * <p/>
 * 自定义参数校验注解
 * <p/>
 * <b>Creation Time:</b> 2020/4/2 17:26.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChannelValidator.class)
public @interface ChannelValid {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
