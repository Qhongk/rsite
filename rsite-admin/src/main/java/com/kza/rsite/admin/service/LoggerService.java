package com.kza.rsite.admin.service;

import com.kza.common.annotations.Logged;
import org.springframework.stereotype.Component;

/**
 * Created by kza on 2015/9/17.
 */
@Component
public class LoggerService {

    @Logged
    public String logged(String str1, String str2) {
        return str1 + str2;
    }
}