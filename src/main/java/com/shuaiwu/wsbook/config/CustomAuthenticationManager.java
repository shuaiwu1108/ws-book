package com.shuaiwu.wsbook.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaiwu.wsbook.entity.Users;
import com.shuaiwu.wsbook.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 在数据库中查找用户
        Users user = iUsersService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, username));
        if (user == null)
            throw new UsernameNotFoundException("User not found with username: " + username);

        // 验证密码是否正确
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 返回认证信息
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        } else {
            log.error("password[{}]", password);
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

