package com.kza.common.type;

/**
 * Created by kza.kong on 2015/5/28.
 */
public enum OrderStatus {

    DESC("1"),ASC("2"),OTHER("0");

    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }

    public String value() {
        return code;
    }
}
