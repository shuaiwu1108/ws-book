package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.shuaiwu.wsbook.entity.Roles;
import com.shuaiwu.wsbook.service.IRolesService;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Slf4j
public class RolesController {

    @Autowired
    private IRolesService iRolesService;

    @Operation(summary = "查询所有角色")
    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object all(){
        Map<Object, Object> rr = MapUtil.builder().put("data", iRolesService.list())
            .put("code", 20000)
            .put("message", "成功").build();
        return rr;
    }

    @Operation(summary = "新增角色")
    @PutMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object put(@RequestBody Roles roles){
        roles.setCreateTime(new Date());
        boolean flag = iRolesService.save(roles);
        Map<Object, Object> rr = MapUtil.builder()
            .put("code", 20000)
            .put("message", "新增成功")
            .put("data", flag)
            .build();
        return rr;
    }


    @Operation(summary = "角色修改")
    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object update(@RequestBody Roles roles){
        roles.setUpdateTime(new Date());
        boolean flag = iRolesService.saveOrUpdate(roles);
        Map<Object, Object> rr = MapUtil.builder()
            .put("code", 20000)
            .put("message", "修改成功")
            .put("data", flag)
            .build();
        return rr;
    }
}
