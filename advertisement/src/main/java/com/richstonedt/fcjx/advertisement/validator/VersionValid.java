package com.richstonedt.fcjx.advertisement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <b><code>VersionValid</code></b>
 * <p/>
 * 版本号校验注解
 * <p/>
 * <b>Creation Time:</b> 2020/5/7 1:57.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = VersionValidator.class)
public @interface VersionValid {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
