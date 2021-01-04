package com.egc.crowd.mvc.config;

import com.egc.crowd.constant.CrowdConstant;
import com.egc.crowd.exception.AccessForbiddenException;
import com.egc.crowd.exception.LoginAcctAlreadyInUseException;
import com.egc.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.egc.crowd.exception.LoginFailedException;
import com.egc.crowd.util.CrowdUtil;
import com.egc.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION: 基于注解的异常处理器类
 * @USER: eugenechow
 * @DATE: 2020/12/26 下午4:01
 */

@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView LoginAcctAlreadyInUseForUpdateException(HttpServletRequest request, HttpServletResponse response, LoginAcctAlreadyInUseException exception) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolverLoginAcctAlreadyInUseException(HttpServletRequest request, HttpServletResponse response, LoginAcctAlreadyInUseException exception) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(HttpServletRequest request, HttpServletResponse response, AccessForbiddenException exception) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(HttpServletRequest request, HttpServletResponse response, LoginFailedException exception) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(HttpServletRequest request, HttpServletResponse response, ArithmeticException exception) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    // @ExceptionHandler将一个具体的异常类和一个方法关联起来
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolverNullPointerException(HttpServletRequest request, HttpServletResponse response, NullPointerException exception) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    private ModelAndView commonResolve(

            // 返回的视图名称
            String viewName,

            // 实际捕获到的异常类型
            Exception exception,

            // 当前请求对象
            HttpServletRequest request,

            // 当前响应对象
            HttpServletResponse response
    ) throws IOException {

        // 1.判断请求的类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);

        // 2.如果是Ajax请求
        if (judgeRequestType) {

            // 3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            // 4.创建Gson对象
            Gson gson = new Gson();

            // 5.将ResultEntity对象转换为Json字符串
            String json = gson.toJson(judgeRequestType);

            // 6.将Json字符串作为响应体返回给浏览器
            response.getWriter().write(json);

            // 7.由于上面已经通过原生的Response对象返回了响应, 所以不提供ModelAndView对象
            return null;
        }

        // 8.如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        // 9.将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATT_NAME_EXCEPTION, exception);

        modelAndView.setViewName(viewName);

        return modelAndView;
    }

}
