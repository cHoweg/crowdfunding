package com.egc.crowd.exception;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION: 登录失败异常
 * @USER: eugenechow
 * @DATE: 2020/12/28 上午9:48
 */
public class LoginFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
