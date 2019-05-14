package com.cloud.common.advice;

import com.cloud.common.bean.ObjectResult;
import com.cloud.common.bean.SystemConstant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局的异常处理切面类
 *
 * @author 7le
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAdvice {

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ObjectResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        if (e.getCause() instanceof JsonMappingException) {
            JsonMappingException cause = (JsonMappingException) e.getCause();
            String propertyName = cause.getPath().get(0).getFieldName();
            return new ObjectResult(SystemConstant.PARAM_ERROR.getCode(), "参数" + propertyName + "格式错误");
        } else if (e.getCause() instanceof JsonParseException) {
            return new ObjectResult(SystemConstant.JSON_ERROR.getCode(), SystemConstant.JSON_ERROR.getMsg());
        } else {
            return new ObjectResult(SystemConstant.PARAM_ERROR.getCode(), SystemConstant.PARAM_ERROR.getMsg());
        }
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ObjectResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new ObjectResult(SystemConstant.METHOD_NOT_ALLOWED.getCode(), SystemConstant.METHOD_NOT_ALLOWED.getMsg());
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ObjectResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);
        return new ObjectResult(SystemConstant.UNSUPPORTED_MEDIA_TYPE.getCode(), SystemConstant.UNSUPPORTED_MEDIA_TYPE.getMsg());
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ObjectResult handleException(Exception e) {
        log.error("服务运行异常", e);
        return new ObjectResult(SystemConstant.SYSTEM_ERROR.getCode(), SystemConstant.SYSTEM_ERROR.getMsg());
    }

    /**
     * 400 - @Valid 校验的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ObjectResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("自定义错误信息", e);
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if (Strings.isNullOrEmpty(message)) {
            return new ObjectResult(SystemConstant.PARAM_ERROR.getCode(), SystemConstant.PARAM_ERROR.getMsg());
        } else {
            return ObjectResult.error(message);
        }
    }
}
