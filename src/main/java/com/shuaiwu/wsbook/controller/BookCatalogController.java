package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.entity.BookCatalog;
import com.shuaiwu.wsbook.service.IBookCatalogService;
import com.shuaiwu.wsbook.service.IBookService;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-04
 */
@Slf4j
@RestController
@RequestMapping("/api/bookCatalog")
public class BookCatalogController {

    @Autowired
    private IBookService iBookService;
    @Autowired
    private IBookCatalogService iBookCatalogService;

    @PostMapping("save")
    public void save() throws InterruptedException {
        iBookCatalogService.saveBookCatalog(iBookService.list());
    }

    @PostMapping("list")
    public Object list(@RequestBody JSONObject jsonObject){
        return iBookCatalogService.list(
            new LambdaQueryWrapper<BookCatalog>()
                .eq(BookCatalog::getBookId, jsonObject.getLong("bookId"))
        );
    }
}
