package com.shuaiwu.wsbook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.shuaiwu.booksystem.entity.Roles;
import net.shuaiwu.booksystem.mapper.RolesMapper;
import net.shuaiwu.booksystem.service.IRolesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {

}
