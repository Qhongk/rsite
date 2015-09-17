package com.kza.rsite.controller;

import com.kza.common.annotations.Logged;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kza on 2015/9/16.
 */
@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @RequestMapping(value = "/logger")
    public void logger(HttpServletRequest request, HttpServletResponse response) {
        logged("hello", "world !");
    }

    @Logged
    private String logged(String str1, String str2) {
        return str1 + str2;
    }
}
