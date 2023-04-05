package com.jie.bookshare.config;

import com.jie.bookshare.common.Result;
import com.jie.bookshare.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * 全局异常捕捉类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 捕获器：缺少参数
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        return Result.error().message(String.format("缺少必要参数[%s]", ex.getParameterName()));
    }

    // 捕获器
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.error().message(null == error ? ResultCode.ERROR_MESSAGE : error.getDefaultMessage());
    }

    // 捕获器
    @ExceptionHandler(value = {BindException.class})
    public Result handleBindException(BindException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.error().message(null == error ? ResultCode.ERROR_MESSAGE : error.getDefaultMessage());
    }

    // 捕获器: 传了值，但是不符合要求
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
        return Result.error().message(first.isPresent() ? first.get().getMessage() : ResultCode.ERROR_MESSAGE);
    }

    // 其他所有异常捕获器
    @ExceptionHandler(Exception.class)
    public Result otherExceptionDispose(Exception e) {
        // 打印错误日志
        log.error("错误代码({}),错误信息({})", ResultCode.ERROR, e.getMessage());
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }
}
