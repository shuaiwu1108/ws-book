package com.shuaiwu.wsbook.service.impl;

import com.shuaiwu.wsbook.entity.Book;
import com.shuaiwu.wsbook.mapper.BookMapper;
import com.shuaiwu.wsbook.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-12-02
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
