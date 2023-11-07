package com.shuaiwu.wsbook.config;

import com.shuaiwu.wsbook.utils.TokenUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Component
public class CustomAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用Caching包装，避免controller无法获取request请求参数
        ContentCachingRequestWrapper requestWrapper = new  ContentCachingRequestWrapper(request);
        try {
            // 从请求头中获取 JWT
            String token = requestWrapper.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isNotBlank(token)) {
                if(token.startsWith("Bearer ")){
                    token = token.substring(7);
                }
                // 验证 JWT 的签名
                if (tokenUtil.validateToken(token)) {
                    // 从 JWT 中获取用户名
                    String username = tokenUtil.getUsernameFromToken(token);

                    // 根据用户名从数据库中加载用户
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 如果用户存在，将用户信息存储到 SecurityContext 中
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                    // 更新token的有效期
                    tokenUtil.refreshToken(token);
                }
            }else{
                log.info("doFilterInternal token is empty [{}]", requestWrapper.getRequestURI());
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        filterChain.doFilter(request, response);
    }
}
