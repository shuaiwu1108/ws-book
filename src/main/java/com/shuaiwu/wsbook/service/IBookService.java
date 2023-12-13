package com.shuaiwu.wsbook.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaiwu.wsbook.dto.BookDTO;
import com.shuaiwu.wsbook.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
public interface IBookService extends IService<Book> {

    void saveBook() throws InterruptedException;

    IPage<BookDTO> getBookListAndAuthorName(JSONObject jsonObject);
}
