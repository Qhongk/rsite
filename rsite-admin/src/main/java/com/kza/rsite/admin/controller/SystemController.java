package com.kza.rsite.admin.controller;

import com.kza.rsite.admin.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kza on 2015/9/16.
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private LoggerService loggerService;

    @RequestMapping(value = "/logger")
    public void logger(HttpServletRequest request, HttpServletResponse response) {
        logged("hello", "world !");
    }

    private String logged(String str1, String str2) {
        return loggerService.logged(str1, str2);
    }
}
