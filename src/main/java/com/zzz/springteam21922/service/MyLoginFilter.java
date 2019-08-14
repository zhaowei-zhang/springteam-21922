//package com.zzz.springteam21922.service;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @description:
// * @version: 1.0
// * @author: zhaowei.zhang01@hand-china.com
// * @date: 2019/8/14
// */
//public class MyLoginFilter extends AbstractAuthenticationProcessingFilter {
//
//    public MyLoginFilter() {
//        super("/api/**");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//
//        String type = obtainParameter(request, "type");
//        String mobile = obtainParameter(request, "mobile");
//        String principal = obtainParameter(request, "username");
//        String credentials = obtainParameter(request, "password");
//
//        System.err.println(type+" "+mobile+" "+principal+" "+credentials);
//
//        return null;
//    }
//
//
//    private String obtainParameter(HttpServletRequest request, String parameter) {
//        return request.getParameter(parameter);
//    }
//}
