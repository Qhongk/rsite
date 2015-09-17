package com.kza.common.annotations;

/**
 * Created by kza on 2015/9/16.
 */
public enum CacheTimePolicy {

    DEFUALT(600), ONEHOUR(3600);

    private int scecond;

    CacheTimePolicy(int scecond) {
        this.scecond = scecond;
    }
}
