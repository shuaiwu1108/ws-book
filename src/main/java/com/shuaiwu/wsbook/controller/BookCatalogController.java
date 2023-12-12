package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.*;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("one")
    public Object one(@RequestBody JSONObject jsonObject) {
        return iBookCatalogService.getBookCatalogContent(jsonObject);
    }

    @PostMapping("getCurrentLocation")
    public Object getLocation(@RequestBody JSONObject jsonObject){
        return iBookCatalogService.getCurrentLocation(jsonObject);
    }

    @PostMapping("getCatalogByLocation")
    public Object getCatalogByLocation(@RequestBody JSONObject jsonObject){
        return iBookCatalogService.getCatalogByLocation(jsonObject);
    }

    @PostMapping("list")
    public Object list(@RequestBody JSONObject jsonObject) {
        Long bookId = jsonObject.getLong("bookId");
        int pageIndex = jsonObject.getInt("pageIndex");
        int pageSize = jsonObject.getInt("pageSize");
        Page<BookCatalog> page = new Page<>(pageIndex, pageSize);
        IPage<BookCatalog> ipage = iBookCatalogService.page(page,
            new LambdaQueryWrapper<BookCatalog>()
                .eq(BookCatalog::getBookId, bookId)
        );
        return ipage.getRecords();
    }
}
