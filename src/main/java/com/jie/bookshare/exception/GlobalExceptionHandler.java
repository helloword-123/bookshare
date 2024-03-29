package com.jie.bookshare.exception;

import com.jie.bookshare.common.Result;
import com.jie.bookshare.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
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
        return Result.error().message(null == error ? ResultCode.MESSAGE_ERROR : error.getDefaultMessage());
    }

    // 捕获器
    @ExceptionHandler(value = {BindException.class})
    public Result handleBindException(BindException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return Result.error().message(null == error ? ResultCode.MESSAGE_ERROR : error.getDefaultMessage());
    }

    // 捕获器: 传了值，但是不符合要求
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        if (log.isErrorEnabled()) {
            log.error(ex.getMessage(), ex);
        }
        Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
        return Result.error().message(first.isPresent() ? first.get().getMessage() : ResultCode.MESSAGE_ERROR);
    }

    // 自定义异常
    @ExceptionHandler(value = {CustomizeRuntimeException.class, CustomizeException.class})
    public Result handleCustomizeException(Exception e) {
        // 打印错误日志
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return Result.error().message(e.getMessage());
    }

    // 权限不足
    @ExceptionHandler(value = {AccessDeniedException.class})
    public Result handleAccessDeniedException(Exception e) {
        // 打印错误日志
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return Result.error().message(ResultCode.MESSAGE_ACCESS_DENIED).code(ResultCode.CODE_ACCESS_DENIED);
    }

    // 其他所有异常捕获器
    @ExceptionHandler(Exception.class)
    public Result otherExceptionDispose(Exception e) {
        // 打印错误日志
        log.error("错误代码({}),错误信息({})", ResultCode.CODE_ERROR, e.getMessage());
        return Result.error().message(ResultCode.MESSAGE_ERROR);
    }
}
