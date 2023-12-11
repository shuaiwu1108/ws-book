package com.shuaiwu.wsbook.service.impl;

import cn.hutool.json.JSONObject;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.entity.BookCatalog;
import com.shuaiwu.wsbook.mapper.BookCatalogMapper;
import com.shuaiwu.wsbook.service.IBookCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaiwu.wsbook.utils.HttpUtil;
import com.shuaiwu.wsbook.utils.JsoupUtil;
import com.shuaiwu.wsbook.utils.MinioUtil;
import com.shuaiwu.wsbook.utils.RedisKeys;
import com.shuaiwu.wsbook.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

    public String getBookCatalogContent(JSONObject jsonObject){
        String catalogFileUrl = jsonObject.getStr("catalogFileUrl");
        if (StringUtil.isNullOrEmpty(catalogFileUrl)){
            String catalotUrl = jsonObject.getStr("catalogUrl");
            String res = HttpUtil.get(catalotUrl, "", null, "GBK");
            if (StringUtil.isNullOrEmpty(res)){
                log.error("章节内容请求失败，book:{}, 章节：{}", jsonObject.getStr("bookId"), jsonObject.getLong("id"));
                return String.format("章节内容请求失败，book:{}, 章节：{}, 请联系管理人员", jsonObject.getStr("bookId"), jsonObject.getLong("id"));
            }else{
                String content = JsoupUtil.bqg_catalogue_content(res);
                return content;
            }
        }else{
            String minioUrl = jsonObject.getStr("minioUrl");
            String res = HttpUtil.getNotSSL(minioUrl.concat(catalogFileUrl), "", null, "UTF-8");
            if (StringUtil.isNullOrEmpty(res)){
                log.error("章节内容请求失败，book:{}, 章节：{}", jsonObject.getStr("bookId"), jsonObject.getLong("id"));
                return String.format("章节内容请求失败，book:{}, 章节：{}, 请联系管理人员", jsonObject.getStr("bookId"), jsonObject.getLong("id"));
            }else{
                return res;
            }
        }
    }


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
//                    log.warn("章节已存在：{}", value);
                    continue;
                }
                BookCatalog bc = new BookCatalog();
                String catalogUrl = b.getUrl().concat(t[1]);
                bc.setCatalogUrl(catalogUrl);
                String catalogRes = HttpUtil.get(catalogUrl, "", null, "GBK");
                if (StringUtil.isNullOrEmpty(catalogRes)) {
                    log.error("章节内容请求失败book:{}", value);
                    continue;
                } else {
                    String catalogContent = JsoupUtil.bqg_catalogue_content(catalogRes);
                    String objName = bookId.concat("/").concat(catalogId).concat(".txt");
                    try (InputStream is = new ByteArrayInputStream(catalogContent.getBytes())){
                        // 存储章节内容至minio
                        boolean f = MinioUtil.putObject("book", objName, is);
                        if (f){
                            bc.setCatalogFileUrl("/book/".concat(objName));
                        }
                    } catch (Exception e){
                        log.error("", e);
                        log.error("章节内容存储minio失败,book:{}", value);
                        continue;
                    }
                }

                try {
                    bc.setBookId(b.getId());
                    bc.setCatalogName(t[0]);
                    bc.setOrderNo(catalogId);
                    bc.currentTime();
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("章节数据存储失败：{}", value);
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
