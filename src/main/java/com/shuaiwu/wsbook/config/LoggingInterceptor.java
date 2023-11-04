package com.shuaiwu.wsbook.config;

import cn.hutool.json.JSONUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import java.util.Map;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new  ContentCachingRequestWrapper(request);
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        log.info("请求路径: {} {}", request.getRequestURL(), request.getMethod());
        log.info("请求参数: {}", JSONUtil.toJsonStr(parameterMap));
        return true;
    }
}
