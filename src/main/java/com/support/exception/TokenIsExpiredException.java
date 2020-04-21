package com.support.exception;

/**
 * @ClassName TokenIsExpiredException
 * @Author 吴俊淇
 * @Date 2020/3/19 13:55
 * @Version 1.0
 **/

/**
 * @description: 自定义异常
 */
public class TokenIsExpiredException extends Exception {


    public TokenIsExpiredException() {
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

