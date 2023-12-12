package com.shuaiwu.wsbook.service;

import cn.hutool.json.JSONObject;
import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.entity.BookCatalog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-04
 */
public interface IBookCatalogService extends IService<BookCatalog> {

    void saveBookCatalog(List<Book> books) throws InterruptedException;

    String getBookCatalogContent(JSONObject jsonObject);

    /**
     * 根据当前catalog对象，获取page{current,total}对象
     * @param jsonObject
     * @return
     */
    Map<String, Integer> getCurrentLocation(JSONObject jsonObject);

    /**
     * 根据下标信息，获取catalog对象
     * @param jsonObject
     * @return
     */
    BookCatalog getCatalogByLocation(JSONObject jsonObject);
}
