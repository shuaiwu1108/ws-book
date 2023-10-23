package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.shuaiwu.booksystem.config.JwtTokenProvider;
import net.shuaiwu.booksystem.entity.Roles;
import net.shuaiwu.booksystem.entity.UserRoles;
import net.shuaiwu.booksystem.entity.Users;
import net.shuaiwu.booksystem.service.IRolesService;
import net.shuaiwu.booksystem.service.IUserRolesService;
import net.shuaiwu.booksystem.service.IUsersService;
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

    @Operation(summary = "获取当前登录用户")
    @GetMapping("info")
    public Object getInfo(Authentication authentication){
        String currentUserName = authentication.getName();
        Users users = iUsersService.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, currentUserName));
        List<UserRoles> userRolesList = iUserRolesService.list(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, users.getId()));
        Set<Long> rolesIds = userRolesList.stream().map(UserRoles::getRoleId).collect(Collectors.toSet());
        List<Roles> rolesList = iRolesService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, rolesIds));
        Map<Object, Object> resMap = MapUtil.builder().put("roles", rolesList.stream().map(Roles::getRolename).collect(Collectors.toSet()))
            .put("name", currentUserName)
            .put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
            .build();
        log.info("start getCurrentUser [{}]", JSONUtil.toJsonStr(resMap));
        Map<Object, Object> rr = MapUtil.builder()
            .put("data", resMap)
            .put("code", 20000)
            .put("message", "成功")
            .build();
        return rr;
    }

    @Operation(summary = "查询所有用户")
    @RequestMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object all(HttpServletRequest httpServletRequest){
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        log.info("all params : {}", JSONUtil.toJsonStr(params));
        Map<Object, Object> rr = MapUtil.builder().put("data", iUsersService.list())
            .put("code", 20000)
            .put("message", "成功").build();
        return rr;
    }

    @Operation(summary = "新增用户")
    @PutMapping("save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object put(@RequestBody Users users){
        return iUsersService.save(users);
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

    @PostMapping("/logout")
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
