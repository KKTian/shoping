package com.study.shoping.aspect;

import com.google.common.collect.Lists;
import com.study.shoping.base.model.vo.ValidVo;
import com.study.shoping.base.enums.http.ServiceStatus;
import com.study.shoping.base.exception.ShopingRunException;
import com.study.shoping.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 描述: controller 异常抛出拦截类，统一返回restful错误格式
 *
 * @outhor KK
 * @create 2018-05-31 18:20
 */
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler{
    @ExceptionHandler(ShopingRunException.class)
    @ResponseStatus(HttpStatus.OK)
    public R notFount(ShopingRunException e) {
        log.error("业务异常", e);
        return R.error(e.getCode(), e.getMessage(),e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public R notFount(IllegalArgumentException e) {
        log.error("参数异常", e);
        return R.error(ServiceStatus.REQUEST_NULL,e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public R accessDenied(AccessDeniedException e) {
        log.error("权限异常", e);
        return R.error(ServiceStatus.ACCESS_DENIED,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public R runtimeError(RuntimeException e) {
        log.error("运行时异常", e);
        return R.error(ServiceStatus.SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public R error404(NoHandlerFoundException e){
        log.error("未找到方法",e);
        return R.error(ServiceStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public R parameterNotFoundError(MissingServletRequestParameterException e){
        log.error("请求参数未发现",e);
        return R.error(ServiceStatus.REQUEST_NULL,e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public R parameterError(BindException e){
        log.error("请求参数验证错误",e);
        //获取参数校验错误集合
        List<FieldError> fieldErrors = e.getFieldErrors();
        final List<ValidVo> validDtos = Lists.newArrayList();
        fieldErrors.stream().forEach(fe ->
            validDtos.add(new ValidVo(fe.getField(),fe.getRejectedValue(),fe.getDefaultMessage()))
        );
        return R.valid(fieldErrors.size(),validDtos);
    }

}