package com.shuaiwu.wsbook.config;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(AccessDeniedException e){
        log.error("授权异常", e.getMessage());
        HashMap<String, String> res = new HashMap<>();
        res.put("code", "50001");
        res.put("status", "error");
        res.put("message", "授权异常");
        return res;
    }

    @ExceptionHandler(AuthenticationException.class)
    public Object authenticationException(AuthenticationException e){
        log.error("认证异常", e.getMessage());
        HashMap<String, String> res = new HashMap<>();
        res.put("code", "50002");
        res.put("status", "error");
        res.put("message", "认证异常");
        return res;
    }

    @ExceptionHandler(Exception.class)
    public Object customHandler(Exception e){
        log.error("未知异常", e);
        HashMap<String, String> res = new HashMap<>();
        res.put("code", "59999");
        res.put("status", "error");
        res.put("message", "未知异常");
        return res;
    }
}
