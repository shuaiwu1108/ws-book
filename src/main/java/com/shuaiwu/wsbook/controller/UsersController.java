package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shuaiwu.wsbook.dto.TreeMenu;
import com.shuaiwu.wsbook.entity.RolesMenu;
import com.shuaiwu.wsbook.service.IMenuService;
import com.shuaiwu.wsbook.service.IRolesMenuService;
import com.shuaiwu.wscommon.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import com.shuaiwu.wsbook.utils.TokenUtil;
import com.shuaiwu.wsbook.entity.Roles;
import com.shuaiwu.wsbook.entity.UsersRoles;
import com.shuaiwu.wsbook.entity.Users;
import com.shuaiwu.wsbook.service.IRolesService;
import com.shuaiwu.wsbook.service.IUserRolesService;
import com.shuaiwu.wsbook.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author shuaiwu
 * @since 2023-09-18
 */
@Slf4j
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IRolesService iRolesService;
    @Autowired
    private IUserRolesService iUserRolesService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IRolesMenuService iRolesMenuService;
    @Autowired
    private IMenuService iMenuService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("info")
    public Object getInfo(Authentication authentication){
        String currentUserName = authentication.getName();
        Users users = iUsersService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, currentUserName));
        List<UsersRoles> usersRolesList = iUserRolesService.list(new LambdaQueryWrapper<UsersRoles>().eq(UsersRoles::getUserId, users.getId()));
        Set<Long> rolesIds = usersRolesList.stream().map(UsersRoles::getRoleId).collect(Collectors.toSet());
        List<Roles> rolesList = iRolesService.listByIds(rolesIds);
        List<RolesMenu> rolesMenuList = iRolesMenuService.list(new LambdaQueryWrapper<RolesMenu>().in(RolesMenu::getRoleId, rolesIds));
        Set<Long> menuIds = rolesMenuList.stream().map(RolesMenu::getMenuId).collect(Collectors.toSet());
        List<TreeMenu> treeMenuList = iMenuService.queryByIds(menuIds);

        Map<Object, Object> resMap = MapUtil.builder()
            .put("roleCodes", rolesList.stream().map(Roles::getRolecode).collect(Collectors.toSet()))
            .put("treeMenus", treeMenuList)
            .put("name", currentUserName)
            .put("avatar", users.getAvatar())
            .build();
        Map<Object, Object> rr = MapUtil.builder()
            .put("data", resMap)
            .put("code", 20000)
            .put("message", "成功")
            .build();
        return rr;
    }


    @Operation(summary = "用户的角色")
    @PostMapping("getUserRoles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object getUserRoles(@RequestBody Users users){
        List<UsersRoles> usersRolesList = iUserRolesService.list(new LambdaQueryWrapper<UsersRoles>().eq(
            UsersRoles::getUserId, users.getId()));
        Set<Long> rolesIds = usersRolesList.stream().map(UsersRoles::getRoleId).collect(Collectors.toSet());
        List<Roles> rolesList = new ArrayList<>();
        if (rolesIds.isEmpty()){

        }else{
            rolesList = iRolesService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, rolesIds));
        }
        return MapUtil.builder()
            .put("data", rolesList)
            .put("code", 20000)
            .put("message", "成功")
            .build();
    }

    @Operation(summary = "用户角色修改")
    @PostMapping("saveUserRoles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object saveUserRoles(@RequestBody JSONObject jsonObject){
        JSONArray roleIds = jsonObject.getJSONArray("roleIds");
        Long userId = jsonObject.getLong("id");

        // 删除用户的所有角色
        iUserRolesService.remove(new LambdaQueryWrapper<UsersRoles>().eq(UsersRoles::getUserId, userId));

        // 循环添加用户的角色
        List<Long> roleIdsl = roleIds.toList(Long.class);
        List<UsersRoles> usersRolesList = new ArrayList<>();
        for (Long roleId : roleIdsl) {
            UsersRoles usersRoles = new UsersRoles();
            usersRoles.setUserId(userId);
            usersRoles.setRoleId(roleId);
            usersRolesList.add(usersRoles);
        }
        boolean flag = iUserRolesService.saveBatch(usersRolesList);

        return MapUtil.builder()
            .put("data", flag)
            .put("code", 20000)
            .put("message", "修改成功")
            .build();
    }

    @Operation(summary = "查询所有用户")
    @RequestMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object all(HttpServletRequest httpServletRequest){
        Map<Object, Object> rr = MapUtil.builder().put("data", iUsersService.list())
            .put("code", 20000)
            .put("message", "成功").build();
        return rr;
    }

    @Operation(summary = "新增用户")
    @PutMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object put(@RequestBody Users users){
        users.setCreateTime(new Date());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        boolean flag = iUsersService.save(users);
        Map<Object, Object> rr = MapUtil.builder()
            .put("code", 20000)
            .put("message", "新增成功")
            .put("data", flag)
            .build();
        return rr;
    }

    @Operation(summary = "修改用户")
    @PostMapping("update")
    public Object update(@RequestBody Users users){
        users.setUpdateTime(new Date());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        boolean flag = iUsersService.saveOrUpdate(users);
        Map<Object, Object> rr = MapUtil.builder()
            .put("code", 20000)
            .put("message", "修改成功")
            .put("data", flag)
            .build();
        return rr;
    }

    @PostMapping("login")
    public Object login(@RequestBody Map<String, String> loginParams){
        // 对用户名和密码进行验证
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginParams.get("username"),
                passwordEncoder.encode(loginParams.get("password"))
            )
        );

        // 生成 JWT
        String token = tokenUtil.generateToken(authentication);

        Map<Object, Object> tokenM = MapUtil.builder().put("token", token).build();

        // 将 JWT 添加到响应头中并返回给客户端
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, token)
            .body(MapUtil.builder()
                .put("data", tokenM)
                .put("code", 20000)
                .put("message", "登录成功")
                .build());
    }

    @PostMapping("logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 记录用户的登出日志
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        redisUtil.remove(token);
        log.info("用户 [{}] 已登出 IP {} 客户端 {}", username, ipAddress, userAgent);

        Map<Object, Object> rr = MapUtil.builder()
            .put("data", "登出完毕")
            .put("code", 20000)
            .put("message", "成功")
            .build();
        return rr;
    }
}
