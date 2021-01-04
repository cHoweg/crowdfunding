package com.egc.crowd.exception;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION: 保存或更新Admin时检测到账户重复时抛出异常
 * @USER: eugenechow
 * @DATE: 2020/12/30 下午5:21
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginAcctAlreadyInUseForUpdateException() {
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
