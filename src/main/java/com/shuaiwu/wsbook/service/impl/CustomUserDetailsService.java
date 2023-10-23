package com.shuaiwu.wsbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.shuaiwu.booksystem.entity.Roles;
import net.shuaiwu.booksystem.entity.UserRoles;
import net.shuaiwu.booksystem.entity.Users;
import net.shuaiwu.booksystem.service.IRolesService;
import net.shuaiwu.booksystem.service.IUserRolesService;
import net.shuaiwu.booksystem.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IRolesService iRolesService;
    @Autowired
    private IUserRolesService iUserRolesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 在数据库中查找用户
        Users users = iUsersService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, username));
        if (users == null)
            new UsernameNotFoundException("User not found with username: " + username);
        List<UserRoles> userRolesList = iUserRolesService.list(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, users.getId()));
        Set<Long> rolesIds = userRolesList.stream().map(UserRoles::getRoleId).collect(Collectors.toSet());
        List<Roles> rolesList = iRolesService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, rolesIds));
        Set<String> rolesNames = rolesList.stream().map(Roles::getRolename).collect(Collectors.toSet());

        // 返回用户信息
        return new UserDetailsImpl(users.getId(), users.getUsername(), users.getPassword(), rolesNames);
    }
}
