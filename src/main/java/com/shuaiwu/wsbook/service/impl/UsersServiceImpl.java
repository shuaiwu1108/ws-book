package com.shuaiwu.wsbook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.shuaiwu.booksystem.entity.Users;
import net.shuaiwu.booksystem.mapper.UsersMapper;
import net.shuaiwu.booksystem.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public IPage<Users> getUsersList(int pageNum, int pageSize) {
        Page<Users> page = new Page<>(pageNum, pageSize);
        return usersMapper.selectUsersList(page);
    }
}
