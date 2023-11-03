package com.shuaiwu.wsbook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaiwu.wsbook.entity.Users;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
public interface IUsersService extends IService<Users> {

    IPage<Users> getUsersList(int pageNum, int pageSize);
}
