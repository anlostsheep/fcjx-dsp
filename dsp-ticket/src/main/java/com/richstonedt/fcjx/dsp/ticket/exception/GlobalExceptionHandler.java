package com.richstonedt.fcjx.dsp.ticket.exception;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.ticket.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.ticket.pojo.dto.MyResponseDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * <b><code>GobalException</code></b>
 * <p/>
 * 全局异常处理器
 * <p/>
 * <b>Creation Time:</b> 2020/1/2 18:06.
 *
 * @author user
 * @since developer-environment
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public MyResponseDTO<Object> handle(Exception e) {
        MyResponseCode internalServerError = MyResponseCode.INTERNAL_SERVER_ERROR;
        MyResponseDTO<Object> response = MyResponseDTO.builder()
                .code(internalServerError.getCode())
                .desc(e.getMessage())
                .data(Lists.newArrayList())
                .build();

        // 处理自定义异常
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            response.setCode( apiException.getCode() );
            Optional.ofNullable( apiException.getData() )
                    .ifPresent(response::setData);
        }

        return response;
    }
}
