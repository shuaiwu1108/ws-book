package com.shuaiwu.wsbook.service;

import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.entity.BookCatalog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

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
}
