package com.shuaiwu.wsbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaiwu.wsbook.entity.Users;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
public interface UsersMapper extends BaseMapper<Users> {
    IPage<Users> selectUsersList(Page<Users> page);
}
