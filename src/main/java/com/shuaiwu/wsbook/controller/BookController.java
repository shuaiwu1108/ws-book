package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.shuaiwu.wsbook.dto.BookDTO;
import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.mapper.BookMapper;
import com.shuaiwu.wsbook.service.IAuthorService;
import com.shuaiwu.wsbook.service.IBookService;
import com.shuaiwu.wsbook.utils.CommonUtil;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private IBookService iBookService;

    @Autowired
    private BookMapper bookMapper;

    @RequestMapping("save")
    public void save() throws InterruptedException {
        iBookService.saveBook();
    }

    @PostMapping("list")
    public Object list(@RequestBody JSONObject jsonObject){
        IPage<BookDTO> pageDto = iBookService.getBookListAndAuthorName(jsonObject);
        return pageDto.getRecords();
    }
}
