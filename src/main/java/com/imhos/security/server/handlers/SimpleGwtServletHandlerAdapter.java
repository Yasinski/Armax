package com.imhos.security.server.handlers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinskiy@gmail.com">Arthur Kasinskiy</a>
 * @updated 06.02.13 21:33
 */
public class SimpleGwtServletHandlerAdapter extends SimpleServletHandlerAdapter {

    private ServletConfig servletConfig;

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Servlet servletHandler = (Servlet) handler;
        if (servletHandler.getServletConfig() == null) {
            servletHandler.init(servletConfig);
        }
        return super.handle(request, response, handler);
    }


    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }
}
