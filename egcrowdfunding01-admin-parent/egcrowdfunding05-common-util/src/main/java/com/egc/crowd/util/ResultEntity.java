package com.egc.crowd.util;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/25 下午4:09
 */

/**
 * 统一项目中Ajax请求返回的结果
 * @param <T>
 */
public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    // 封装当前请求处理结果是否成功失败
    private String result;

    // 请求处理失败时返回的错误消息
    private String message;

    // 要返回的数据
    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用工具
     *
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData() {
        return new ResultEntity<Type>(SUCCESS, null, null);
    }

    /**
     * 请求处理成功且需要返回数据时使用的工具
     *
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<Type>(SUCCESS, null, data);
    }

    /**
     * 请求处理失败返回错误信息方法
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message) {
        return new ResultEntity<Type>(FAILED, message, null);
    }

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
