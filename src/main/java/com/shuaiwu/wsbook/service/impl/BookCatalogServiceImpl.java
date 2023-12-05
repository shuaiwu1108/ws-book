package com.shuaiwu.wsbook.service.impl;

import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.entity.BookCatalog;
import com.shuaiwu.wsbook.mapper.BookCatalogMapper;
import com.shuaiwu.wsbook.service.IBookCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-04
 */
@Slf4j
@Service
public class BookCatalogServiceImpl extends ServiceImpl<BookCatalogMapper, BookCatalog> implements IBookCatalogService {


    @Override
    public void saveBookCatalog(List<Book> books) throws InterruptedException {
        for (Book b : books) {
            List<BookCatalog> tmps = new ArrayList<>();
            String res = HttpUtil.get(b.getUrl(), "", null, "GBK");
            String bookId = b.getUrl().split("/")[4];
            if (StringUtil.isNullOrEmpty(res)){
                log.error("书籍章节请求失败book:{}", bookId);
                RedisUtil.sSet(RedisKeys.BQG_BOOK_CATALOG_ERR.name(), bookId);
                continue;
            }

            List<String> catalogs = JsoupUtil.bqg_catalogue_single(res);
            for (String catalog : catalogs){
                String[] t = catalog.split("======");
                String catalogId = t[1].split("\\.")[0].replaceAll("=", "");
                String value = bookId.concat("-").concat(catalogId);
                if (RedisUtil.sHasKey(RedisKeys.BQG_BOOK_CATALOG.name(), value)){
                    log.warn("章节已存在：{}", value);
                    continue;
                }

                BookCatalog bc = new BookCatalog();
                try {
                    bc.setBookId(b.getId());
                    bc.setCatalogName(t[0]);
                    bc.setOrderNo(catalogId);
                    bc.setCatalogUrl(b.getUrl().concat(t[1]));
                    bc.currentTime();
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("章节存储失败：{}", value);
                    continue;
                }
                //redis中存储章节id信息
                RedisUtil.sSet(RedisKeys.BQG_BOOK_CATALOG.name(), value);
                tmps.add(bc);
            }

            Thread.sleep(1000 * 1);
            // 将章节排序后，插入数据库
            Collections.sort(tmps);
            this.saveBatch(tmps);
        }
    }
}
