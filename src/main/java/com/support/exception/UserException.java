package com.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName UserException
 * @Author 吴俊淇
 * @Date 2020/3/21 20:19
 * @Version 1.0
 * @description: 自定义异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserException extends RuntimeException {
    public UserException() {
        super();
    }

    public UserException(Object message) {
        super((String) message);
    }
}
