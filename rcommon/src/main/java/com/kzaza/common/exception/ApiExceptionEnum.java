package com.kzaza.common.exception;

/**
 * Created by hon.kong on 2015/8/14.
 */
public enum ApiExceptionEnum {

    UNKNOWN(-1, "unknown error."),
    SUCCESS(1, "success");

    private int code;

    private String message;

    private ApiExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ApiException error(Object...args) {
        return new ApiException(code, String.format(message, args));
    }
}
