package xit.gateway.core.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import xit.gateway.api.jwt.AuthTokenHandler;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class TestJJWT {
    @Autowired
    private AuthTokenHandler handler;

    @Test
    void test(){
        // 创建JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder();
        // 直接设置Header（此外还支持使用Map设置Header，或者还可以调用setHeaderParam设置单个Header键值对）
        String jwtToken = jwtBuilder.setHeader(Maps.newHashMap("test", "123"))
                /* 设置载荷 */
                // 载荷——自定义键和值
                .claim("username", "tom")
                .claim("role", "admin")
                // 载荷——主题
                .setSubject("token-test")
                // 载荷——过期时间（这里设置为了1天，即从当前时间算起，1天后过期）
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                // 载荷——唯一标识
                .setId(UUID.randomUUID().toString())
                /* 设置签名（这里使用的HS256是对称加密算法，而第二个参数就是密钥） */
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                /* 将三部分拼接为token */
                .compact();
        Assertions.assertNotEquals(null, jwtToken);
    }

    @Test
    void getBcryptPwdForTest(){
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
    }

    @Test
    void testTokenParse(){
        String token = handler.newToken(112233L);
        System.out.println(handler.parse(token));
    }
}
