package com.kzaza.common.exception;

/**
 * Created by kza on 2015/8/17.
 */
public class ApiException extends Exception {

    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
