package com.kza.rsite.appconf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by kza on 2015/9/25.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.kza.rsite.controller.", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})
})
public class MvcConfig extends WebMvcConfigurationSupport {
}
