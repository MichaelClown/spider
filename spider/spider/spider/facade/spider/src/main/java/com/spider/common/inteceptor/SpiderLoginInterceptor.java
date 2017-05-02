package com.spider.common.inteceptor;

import com.spider.common.constant.SessionConstant;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jian.Michael on 2017/4/1.
 */
public class SpiderLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            if (null == request.getSession().getAttribute(SessionConstant.USER_ID)) {
                response.sendRedirect("/account/toLogin");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
