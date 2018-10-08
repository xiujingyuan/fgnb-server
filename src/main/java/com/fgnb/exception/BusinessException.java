package com.fgnb.exception;

/**
 * Created by jiangyitao.
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,Throwable e){
        super(msg,e);
    }

}
