package com.example.walletservice.config;

import com.example.walletservice.util.ClientRequestInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CustomInterceptor implements HandlerInterceptor{

    @Autowired
    ClientRequestInfo clientRequestInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        clientRequestInfo.setClientIpAdress(request.getRemoteAddr());
        clientRequestInfo.setClientUrl(request.getRequestURI());
        clientRequestInfo.setSessionActivityId(request.getSession().getId());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
