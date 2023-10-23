package com.shuaiwu.wsbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.shuaiwu.booksystem.entity.Roles;
import net.shuaiwu.booksystem.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Tag(name = "角色相关接口")
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private IRolesService iRolesService;

    @Operation(summary = "查询所有角色")
    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object all(){
        return iRolesService.list();
    }

    @Operation(summary = "新增角色")
    @PutMapping("save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object put(@RequestBody Roles roles){
        return iRolesService.save(roles);
    }
}
