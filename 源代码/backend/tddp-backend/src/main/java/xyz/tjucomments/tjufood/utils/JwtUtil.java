package xyz.tjucomments.tjufood.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static long expiration;
    private static String secret;

    @Value("${tjufood.jwt.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.expiration = expiration;
    }

    @Value("${tjufood.jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    /**
     * 生成加密后的秘钥
     */
    public static SecretKey generalKey() {
        // --- 关键修改 ---
        // 使用 jjwt 的 Keys.hmacShaKeyFor 方法，
        // 根据 secret 字符串生成一个适用于 HMAC-SHA 算法的、安全的密钥。
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建JWT
     * @param subject 用户ID
     * @return Token字符串
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .setIssuer("TjuFood")
                .setIssuedAt(new Date())
                // 使用正确的密钥和签名算法
                .signWith(generalKey(), SignatureAlgorithm.HS256);

        if (expiration >= 0) {
            long expMillis = System.currentTimeMillis() + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     * @param jwt Token字符串
     * @return Claims
     */
    public static Claims parseJWT(String jwt) {
        // --- 关键修改：使用官方推荐的 parserBuilder ---
        return Jwts.parserBuilder()
                .setSigningKey(generalKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}