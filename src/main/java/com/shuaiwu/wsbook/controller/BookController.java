package com.shuaiwu.wsbook.controller;

import cn.hutool.json.JSONUtil;
import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.service.IAuthorService;
import com.shuaiwu.wsbook.service.IBookService;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
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
@RequestMapping("/wsbook/book")
public class BookController {

    @Autowired
    private IAuthorService iAuthorService;
    @Autowired
    private IBookService iBookService;

    @PostConstruct
    public void init() {
        // 开始拉取并解析存储小说数据
        String res = HttpUtil.get("https://www.xbiquge.bz", "/", null, "GBK");
        try {
            List<String> types = JsoupUtil.bqg_index(res);

            Thread.sleep(1000 * 5);

            for (String type : types) {
                log.info("type===>{}", type);
                String typeRes = HttpUtil.get("https://www.xbiquge.bz", type, null, "GBK");

                Thread.sleep(1000 * 5);

                List<String> books = JsoupUtil.bqg_type(typeRes);

                List<Book> bookList = new ArrayList<>();
                for(String book : books){
                    Book b = new Book();
                    log.info("book===>{}", book);
                    b.setUrl(book);
                    String catalogue = HttpUtil.get(book, "", null, "GBK");

                    Thread.sleep(1000 * 5);

                    Map<String, String> stringMap = JsoupUtil.bqg_catalogue(catalogue);
                    String catalogueJson = JSONUtil.toJsonStr(stringMap);
                    b.setName(stringMap.get("name"));

                    Author a = new Author();
                    a.setName(stringMap.get("author"));
                    a.setCreateTime(new Date());
                    a.setUpdateTime(new Date());
                    iAuthorService.save(a);

                    b.setAuthorId(a.getId());
                    b.setDescription(stringMap.get("description"));
                    b.setCreateTime(new Date());
                    b.setUpdateTime(new Date());
                    b.setWorkStatus("300001");

                    log.info("bookDescription===>{}", catalogueJson);
                    bookList.add(b);
                }

                iBookService.saveBatch(bookList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
