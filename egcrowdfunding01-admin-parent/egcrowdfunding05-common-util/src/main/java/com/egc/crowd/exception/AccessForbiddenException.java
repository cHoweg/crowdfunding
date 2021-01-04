package com.egc.crowd.exception;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION: 用户没有登录就访问受保护资源时抛的异常
 * @USER: eugenechow
 * @DATE: 2020/12/28 下午3:51
 */
public class AccessForbiddenException extends RuntimeException{

    private static final long serialVersionUID = 1;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
