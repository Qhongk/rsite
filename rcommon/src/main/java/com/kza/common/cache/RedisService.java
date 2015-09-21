package com.kza.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by kza on 2015/9/18.
 */
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put() {
        
    }
}
