package com.egc.crowd.exception;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION: 保存或更新Admin时检测到账户重复时抛出异常
 * @USER: eugenechow
 * @DATE: 2020/12/30 下午5:21
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
