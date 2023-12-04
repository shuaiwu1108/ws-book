package com.shuaiwu.wsbook.controller;

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
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("save")
    public void save() throws InterruptedException {
        List<Book> books = iBookService.list();
        for (Book b : books) {
            if (b.getCatalogSize() > 0) {
                iBookCatalogService.remove(new LambdaQueryWrapper<BookCatalog>().eq(BookCatalog::getBookId, b.getId())); //先删除，在保存
                List<BookCatalog> tmps = new ArrayList<>();
                String res = HttpUtil.get(b.getUrl(), "", null, "GBK");
                if (StringUtil.isNullOrEmpty(res)){
                    String bookId = b.getUrl().split("/")[4];
                    log.error("书籍章节请求失败book:{}", bookId);
                    RedisUtil.sSet(RedisKeys.BQG_BOOK_CATALOG_ERR.name(), bookId);
                    continue;
                }

                List<String> catalogs = JsoupUtil.bqg_catalogue_single(res);
                for (String catalog : catalogs){
                    String[] t = catalog.split("======");
                    BookCatalog bc = new BookCatalog();
                    try {
                        bc.setBookId(b.getId());
                        bc.setCatalogName(t[0]);
                        bc.setCatalogUrl(t[1]);
                        bc.currentTime();
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error(e.getMessage());
                        continue;
                    }
                    tmps.add(bc);
                }

                Thread.sleep(1000 * 5);
                iBookCatalogService.saveBatch(tmps);
            }
        }
    }
}
