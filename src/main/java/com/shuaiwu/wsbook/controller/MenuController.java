package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import com.shuaiwu.wsbook.entity.Menu;
import com.shuaiwu.wsbook.service.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-03
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @Operation(summary = "查询菜单")
    @GetMapping("query")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object query(){
        Map<Object, Object> rr = MapUtil.builder()
            .put("data", iMenuService.queryAll())
            .put("code", 20000)
            .put("message", "查询成功")
            .build();
        return rr;
    }

    @Operation(summary = "新增菜单")
    @PutMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object create(@RequestBody Menu menu){
        menu.setCreateTime(new Date());
        boolean flag = iMenuService.save(menu);
        return MapUtil.builder().put("code", 20000)
            .put("data", flag)
            .put("message", "新增成功")
            .build();
    }

    @Operation(summary = "更新菜单")
    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object update(@RequestBody Menu menu){
        menu.setUpdateTime(new Date());
        boolean flag = iMenuService.saveOrUpdate(menu);
        return MapUtil.builder().put("code", 20000)
            .put("data", flag)
            .put("message", "修改成功")
            .build();
    }
}
