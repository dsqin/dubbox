package com.alibaba.dubbo.validation.support.methodvalidation.exception;

/**
 * method param valid exception
 * Created by dsqin on 2016/11/22.
 */
public class ParamFieldValidationException extends Exception {

    /**
     * error code
     */
    private String code;

    public String getCode() {
        return code;
    }

    public ParamFieldValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ParamFieldValidationException(String code) {
        this.code = code;
    }

    public ParamFieldValidationException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public ParamFieldValidationException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public ParamFieldValidationException() {

    }
}
