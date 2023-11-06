package com.shuaiwu.wsbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.shuaiwu.wsbook.entity.UsersRoles;
import com.shuaiwu.wsbook.service.IUserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户-角色关联表 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Tag(name = "用户角色关联接口")
@RestController
@RequestMapping("/api/userRoles")
public class UserRolesController {

    @Autowired
    private IUserRolesService iUserRolesService;

    @Operation(summary = "查询所有用户角色关联")
    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object all(){
        return iUserRolesService.list();
    }

    @Operation(summary = "新增用户角色关联")
    @PutMapping("save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object put(@RequestBody UsersRoles usersRoles){
        return iUserRolesService.save(usersRoles);
    }
}
