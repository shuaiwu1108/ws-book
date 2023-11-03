package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import com.shuaiwu.wsbook.config.JwtTokenProvider;
import com.shuaiwu.wsbook.entity.Roles;
import com.shuaiwu.wsbook.entity.UserRoles;
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
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("info")
    public Object getInfo(Authentication authentication){
        String currentUserName = authentication.getName();
        Users users = iUsersService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, currentUserName));
        List<UserRoles> userRolesList = iUserRolesService.list(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, users.getId()));
        Set<Long> rolesIds = userRolesList.stream().map(UserRoles::getRoleId).collect(Collectors.toSet());
        List<Roles> rolesList = iRolesService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, rolesIds));
        Map<Object, Object> resMap = MapUtil.builder().put("roles", rolesList.stream().map(Roles::getRolecode).collect(Collectors.toSet()))
            .put("name", currentUserName)
            .put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
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
        List<UserRoles> userRolesList = iUserRolesService.list(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, users.getId()));
        Set<Long> rolesIds = userRolesList.stream().map(UserRoles::getRoleId).collect(Collectors.toSet());
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
        iUserRolesService.remove(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, userId));

        // 循环添加用户的角色
        List<Long> roleIdsl = roleIds.toList(Long.class);
        List<UserRoles> userRolesList = new ArrayList<>();
        for (Long roleId : roleIdsl) {
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(userId);
            userRoles.setRoleId(roleId);
            userRolesList.add(userRoles);
        }
        boolean flag = iUserRolesService.saveBatch(userRolesList);

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
        Map<String, String[]> params = httpServletRequest.getParameterMap();
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object update(@RequestBody Users users){
        users.setUpdateTime(new Date());
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
        log.info("start login from front: {}", JSONUtil.toJsonStr(loginParams));
        // 对用户名和密码进行验证
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginParams.get("username"),
                loginParams.get("password")
            )
        );

        // 生成 JWT
        String token = jwtTokenProvider.generateToken(authentication);
        log.info("token: [{}]", token);

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
        log.info("User [{}] logged out from IP address {} using {}", username, ipAddress, userAgent);

        Map<Object, Object> rr = MapUtil.builder()
            .put("data", "登出完毕")
            .put("code", 20000)
            .put("message", "成功")
            .build();
        return rr;
    }
}
