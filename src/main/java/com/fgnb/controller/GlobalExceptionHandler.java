package com.fgnb.controller;

import com.fgnb.exception.BusinessException;
import com.fgnb.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiangyitao.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //未处理的异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e){
        log.error("未处理的异常",e);
        return Response.error(e.getMessage());
    }

    //业务异常
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Response handleBusinessException(BusinessException e){
        log.error(e.getMessage(),e);
        return Response.fail(e.getMessage());
    }

    //参数校验异常
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Response handleBindException(BindException e, HttpServletRequest req){
        log.error("[{}]参数校验错误:{}",req.getServletPath(),e.getFieldError().getDefaultMessage());
        return Response.fail(e.getFieldError().getDefaultMessage());
    }

    //参数校验异常 json
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req){
        log.error("[{}]参数校验错误:{}",req.getServletPath(),e.getBindingResult().getFieldError().getDefaultMessage());
        return Response.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }


}
