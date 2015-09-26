package com.kza.common.appconf;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by zaza on 2015/9/26.
 */
@Configuration
@Import(value = {RedisConfig.class})
@EnableCaching
public class CacheConfig {
}
