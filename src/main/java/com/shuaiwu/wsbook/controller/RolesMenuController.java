package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaiwu.wsbook.dto.TreeMenu;
import com.shuaiwu.wsbook.entity.RolesMenu;
import com.shuaiwu.wsbook.service.IMenuService;
import com.shuaiwu.wsbook.service.IRolesMenuService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-11-06
 */
@RestController
@RequestMapping("/api/rolesMenu")
@Slf4j
public class RolesMenuController {

    @Autowired
    private IRolesMenuService iRolesMenuService;
    @Autowired
    private IMenuService iMenuService;

    @Operation(summary = "角色的权限查询")
    @GetMapping("queryRolePermission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object query(@RequestParam("roleId") Long roleId){
        List<RolesMenu> rolesMenuList = iRolesMenuService.list(new LambdaQueryWrapper<RolesMenu>().eq(RolesMenu::getRoleId, roleId));

        // role角色的菜单权限
        Set<Long> menuIds = rolesMenuList.stream().map(RolesMenu::getMenuId).collect(Collectors.toSet());

        // 菜单树
        List<TreeMenu> treeMenuList = iMenuService.queryAll();

        Map<Object, Object> data = MapUtil.builder()
            .put("menuIds", menuIds)
            .put("treeMenuList", treeMenuList)
            .build();

        return MapUtil.builder()
            .put("data", data)
            .put("code", 20000)
            .put("message", "查询成功")
            .build();
    }

    @Operation(summary = "保存角色权限")
    @PostMapping("saveMenuPermission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object saveMenuPermission(@RequestBody JSONObject jsonObject) {
        Long roleId = jsonObject.getLong("roleId");
        JSONArray menuIds = jsonObject.getJSONArray("menuIds");
        iRolesMenuService.remove(new LambdaQueryWrapper<RolesMenu>().eq(RolesMenu::getRoleId, roleId));
        List<Long> longs = menuIds.toList(Long.class);
        List<RolesMenu> rolesMenuList = new ArrayList<>();
        for (Long l : longs) {
            RolesMenu rm = new RolesMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(l);
            rolesMenuList.add(rm);
        }
        boolean flag = iRolesMenuService.saveBatch(rolesMenuList);
        return MapUtil.builder()
            .put("data", flag)
            .put("code", 20000)
            .put("message", "保存成功")
            .build();
    }
}
