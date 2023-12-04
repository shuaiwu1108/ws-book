package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONUtil;
import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.service.IAuthorService;
import com.shuaiwu.wsbook.service.IBookService;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private IAuthorService iAuthorService;
    @Autowired
    private IBookService iBookService;

    @RequestMapping("save")
    public void save() throws InterruptedException {
        iBookService.saveBook();
    }
}
