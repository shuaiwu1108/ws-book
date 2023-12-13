package com.shuaiwu.wsbook.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * controller控制器日志拦截处理器
 * 2023-12-13 11:14
 */
@Aspect
@Order(5)
@Component
@Slf4j
public class ControllerLogAspect {
    @Pointcut("execution(public * com.shuaiwu.wsbook.controller..*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("Servlet url:{}", request.getRequestURL());
        String contentType = request.getContentType();
        printFormParam(request);
        printBodyParam(contentType, joinPoint);
    }

    private void printBodyParam(String contentType, JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    continue;
                }
                //打印json参数
                if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                    log.info("Servlet Request body: {}", JSONUtil.toJsonStr(arg));
                }
            }
        }
    }

    private void printFormParam(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (!parameterMap.isEmpty()) {
            StringBuilder param = new StringBuilder();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                param.append(entry.getKey()).append("-").append(Arrays.toString(entry.getValue())).append("  ");
            }
            log.info("Servlet Request Form: {}", param.toString());
        }
    }

}



