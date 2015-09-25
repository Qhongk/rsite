package com.kza.rsite.web;

import com.kza.rsite.appconf.MvcConfig;
import com.kza.rsite.appconf.WebApplicationConfig;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by kza on 2015/9/24.
 */
@Order(1)
public class CommonWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebApplicationConfig.class);

        servletContext.setInitParameter("log4jConfigLocation", "classpath:log4j.xml");

        /* listener */
        servletContext.addListener(Log4jConfigListener.class);
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));
        servletContext.addListener(RequestContextListener.class);

        /* filter */
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(
                "encodingFilter", characterEncodingFilter);
        filterRegistration.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

        /* servlet */
        AnnotationConfigWebApplicationContext mvcApplicationContext = new AnnotationConfigWebApplicationContext();
        mvcApplicationContext.register(MvcConfig.class);
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("DispatcherServlet", new DispatcherServlet(mvcApplicationContext));
        servlet.addMapping("/*");
    }
}
