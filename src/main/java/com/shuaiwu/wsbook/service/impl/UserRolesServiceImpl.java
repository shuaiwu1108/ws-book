package com.shuaiwu.wsbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaiwu.wsbook.entity.UsersRoles;
import com.shuaiwu.wsbook.mapper.UserRolesMapper;
import com.shuaiwu.wsbook.service.IUserRolesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UsersRoles> implements IUserRolesService {

}
