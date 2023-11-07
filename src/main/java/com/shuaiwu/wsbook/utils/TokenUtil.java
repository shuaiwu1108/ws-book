package com.shuaiwu.wsbook.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.shuaiwu.wsbook.config.TokenValidateException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenUtil {

    @Value("${token.expiration}")
    private long expiration;

    @Autowired
    private RedisUtil redisUtil;

    public String generateToken(Authentication authentication) {
        // 从认证信息中获取用户信息和权限信息
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
            Collectors.toList());

        String token = SecureUtil.aes().encryptBase64(UUID.randomUUID().toString());
        Map<Object, Object> build = MapUtil.builder()
            .put("userName", authentication.getName())
            .put("roles", roles)
            .build();
        redisUtil.set(token, build, expiration);
        log.info("已生成Token[{}], 过期时间为[{}]", token, expiration);
        return token;
    }

    public boolean validateToken(String token) {
        // 验证 JWT 的签名和有效期
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (!redisUtil.hasKey(token))
            throw new TokenValidateException("Token不存在或已失效");
        return true;
    }

    public String getUsernameFromToken(String token) {
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Map<Object, Object> val = redisUtil.get(token, Map.class);
        Object userName = val.get("userName");
        return userName.toString();
    }

//    public List<String> getRolesFromToken(String token) {
//        // 从 JWT 中获取权限信息
//        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        return (List<String>) claims.get("roles");
//    }

    public void refreshToken(String token){
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long t = redisUtil.getExpire(token);


        // 刷新token
        if (t != null && t != 0 && expiration - t >= 3600) {
            redisUtil.expire(token, expiration);
            log.info("token[{}]最新过期时间[{}]", token, redisUtil.getExpire(token));
        }
    }
}

