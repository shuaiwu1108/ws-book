package com.shuaiwu.wsbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan({"com.shuaiwu.wsbook.mapper"})
public class WsBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsBookApplication.class, args);
    }
}
