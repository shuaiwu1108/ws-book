package com.shuaiwu.wsbook.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Authentication authentication) {
        // 从认证信息中获取用户信息和权限信息
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
            Collectors.toList());

        // 设置 JWT 的过期时间
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        // 生成 JWT
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            // 验证 JWT 的签名和有效期
            if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported", e);
        } catch (MalformedJwtException e) {
            log.error("JWT token is invalid", e);
        } catch (SignatureException e) {
            log.error("JWT signature is invalid", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        // 从 JWT 中获取用户名
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        // 从 JWT 中获取权限信息
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("roles");
    }
}

