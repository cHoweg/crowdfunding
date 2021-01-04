package com.egc.crowd.mvc.interceptor;

import com.egc.crowd.bean.Admin;
import com.egc.crowd.constant.CrowdConstant;
import com.egc.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/28 下午3:45
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.通过request获取session对象
        HttpSession session = request.getSession();

        // 2.尝试从session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATT_NAME_LOGIN_ADMIN);

        // 3.判断admin是否为空
        if (admin == null) {

            // 4.抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        // 5.如果不为空则返回true放行
        return true;
    }
}
