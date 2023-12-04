package com.shuaiwu.wsbook.service.impl;

import cn.hutool.json.JSONUtil;
import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.mapper.BookMapper;
import com.shuaiwu.wsbook.service.IAuthorService;
import com.shuaiwu.wsbook.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private IAuthorService iAuthorService;

    @Override
    public void saveBook() throws InterruptedException {
        // 开始拉取并解析存储小说数据
        String res = HttpUtil.get("https://www.xbiquge.bz", "/", null, "GBK");
        List<String> types = JsoupUtil.bqg_index(res);

        Thread.sleep(1000 * 5);

        for (String type : types) {
            log.info("type===>{}", type);
            String typeRes = HttpUtil.get("https://www.xbiquge.bz", type, null, "GBK");
            if (StringUtil.isNullOrEmpty(typeRes)) {
                log.error("书籍类型请求失败，type:{}", type);
                continue;
            }

            Thread.sleep(1000 * 5);

            List<String> books = JsoupUtil.bqg_type(typeRes);

            List<Book> bookList = new ArrayList<>();
            for (String book : books) {
                Book b = new Book();
                log.info("book===>{}", book);
                String bookId = book.split("/")[4];
                //redis检查书籍Id是否重复
                if (RedisUtil.sHasKey(RedisKeys.BQG_BOOK.name(), bookId)) {
                    log.info("书籍{}已存在", bookId);
                    continue;
                }

                b.setUrl(book);
                String catalogue = HttpUtil.get(book, "", null, "GBK");
                if (StringUtil.isNullOrEmpty(catalogue)) {
                    log.error("书籍数据请求失败，book:{}", book);
                    RedisUtil.sSet(RedisKeys.BQG_BOOK_ERR.name(), bookId);
                    continue;
                }

                Thread.sleep(1000 * 5);

                Map<String, String> stringMap = JsoupUtil.bqg_catalogue(catalogue);
                String catalogueJson = JSONUtil.toJsonStr(stringMap);
                log.info("bookDescription===>{}", catalogueJson);
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
                b.setCatalogSize(Long.parseLong(stringMap.get("catalogueSize")));

                //redis保存书籍id
                RedisUtil.sSet(RedisKeys.BQG_BOOK.name(), bookId);
                bookList.add(b);
            }

            this.saveBatch(bookList);
        }
    }
}
