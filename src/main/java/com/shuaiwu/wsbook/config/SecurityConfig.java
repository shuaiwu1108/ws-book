package com.shuaiwu.wsbook.config;

import com.shuaiwu.wsbook.utils.TokenUtil;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启后可在RestController方法上，使用鉴权注解，如：@PreAuthorize("hasRole('ROLE_TEST')")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//会话管理
            .and()
            .authorizeRequests()
            .antMatchers("/**/*.css","/**/*.js","/imgs/**", "/**/*.html").permitAll()
            .antMatchers("/api/user/login", "/api/user/logout").permitAll()
            .antMatchers("/booksystem/menu/doc", "/v3/**", "/quartz/**", "/api/**").permitAll()
            .anyRequest().authenticated()
            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().logoutUrl("/api/user/logout").permitAll()
//            .and()
            .addFilterAfter(new CustomAuthenticationTokenFilter(tokenUtil,userDetailsService,resolver), UsernamePasswordAuthenticationFilter.class)
            .csrf().disable()
            ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("SELECT username,password,status as enabled FROM users WHERE username=? and status=1")
            .authoritiesByUsernameQuery("SELECT u.username,r.rolecode role FROM users_roles ur"
                + " join users u on ur.user_id=u.id and u.status=1 "
                + " join roles r on ur.role_id=r.id and r.status=1 "
                + " where u.username=?")
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

