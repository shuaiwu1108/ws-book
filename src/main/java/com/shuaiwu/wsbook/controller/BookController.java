package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shuaiwu.wsbook.dto.BookDTO;
import com.shuaiwu.wsbook.mapper.BookMapper;
import com.shuaiwu.wsbook.service.IBookService;
import lombok.extern.slf4j.Slf4j;
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
