package com.richstonedt.fcjx.advertisement.exception;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.advertisement.api.BaseApiResponse;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * <b><code>GlobalExceptionHandler</code></b>
 * <p/>
 * 全局异常类处理
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 18:08.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseApiResponse defaultExceptionHandler(Exception e) {

        if (e instanceof SmartPushException) {
            log.error("广告业务执行异常", e);
            SmartPushException smartPushException = (SmartPushException) e;
            return BaseApiResponse.builder()
                    .code(smartPushException.getCode())
                    .message(smartPushException.getMessage())
                    .data(Lists.newArrayList())
                    .build();
        }

        if (e instanceof HttpMessageConversionException) {
            log.error("请求参数转换异常", e);
            return BaseApiResponse.builder()
                    .code(ApiEnum.BAD_REQUEST_PARAM.getCode())
                    .message(ApiEnum.BAD_REQUEST_PARAM.getMessage())
                    .data(Lists.newArrayList())
                    .build();
        }

        if (e instanceof MethodArgumentNotValidException) {
            log.error("请求参数校验生效", e);
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return BaseApiResponse.builder()
                    .code(ApiEnum.BAD_REQUEST_PARAM.getCode())
                    .message(exception.getMessage())
                    .data(Lists.newArrayList())
                    .build();
        }

        if (e instanceof ConstraintViolationException) {
            log.error("校验参数注解生效", e);
            ConstraintViolationException exception = (ConstraintViolationException) e;

            return BaseApiResponse.builder()
                    .code(ApiEnum.BAD_REQUEST_PARAM.getCode())
                    .message(exception.getLocalizedMessage())
                    .data(Lists.newArrayList())
                    .build();

        }

        if (e instanceof MissingServletRequestParameterException) {
            log.error("请求方式忽略参数异常", e);
            MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
            return BaseApiResponse.builder()
                    .code(ApiEnum.BAD_REQUEST_PARAM.getCode())
                    .message(exception.getMessage())
                    .data(Lists.newArrayList())
                    .build();
        }

        log.error("其他业务处理异常", e);
        return BaseApiResponse.builder()
                .code(ApiEnum.BAD_REQUEST.getCode())
                .message(ApiEnum.BAD_REQUEST.getMessage())
                .data(Lists.newArrayList())
                .build();
    }

}
