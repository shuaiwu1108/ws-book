package com.shuaiwu.wsbook.service.impl;

import com.shuaiwu.wsbook.entity.Author;
import com.shuaiwu.wsbook.mapper.AuthorMapper;
import com.shuaiwu.wsbook.service.IAuthorService;
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
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

}
