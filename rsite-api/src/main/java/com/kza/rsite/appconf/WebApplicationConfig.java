package com.kza.rsite.appconf;

import com.kza.common.appconf.MongoConfig;
import com.kza.common.appconf.RedisConfig;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Created by kza on 2015/9/24.
 */
@Configuration
@ComponentScan(basePackages = "com.kza.*", excludeFilters = {@ComponentScan.Filter(value = {Controller.class})})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(value = {RedisConfig.class, MongoConfig.class})
@PropertySource(value = "classpath:globals.properties", name = "properties")
public class WebApplicationConfig {
}
