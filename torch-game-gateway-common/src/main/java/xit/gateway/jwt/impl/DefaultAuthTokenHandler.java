package xit.gateway.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import xit.gateway.api.jwt.AuthTokenHandler;
import xit.gateway.pojo.AuthToken;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class DefaultAuthTokenHandler implements AuthTokenHandler {
    private final String SECRET = "VmpKMFYyRnJOVmhXYkdSb1RUSjRhRlJVUmt0aFJsSllaRWRHVGxKdFVucFdSbWh2WVZkS1NHVkdjRmRXZWtVd1dWUkJlR05zWkZWU2JGWlRZa1Z3U0Zkc1dsWmxSVFZ6Vm14V1UyRjZiRzlXYTFaSFRrWmFTR1ZHVGxkaGVrWlhXbFZhYjFVeVNuVlJiV2hXWVd0S2FGUnRlR3RqYkZKWldrZDBVMVpGV2pSV1YzaHZaREZTYzFkcldtbFNSbXRzVFRCUkpUTkU==";
    private final SecretKey secretKey;
    private final JwtParser parser;
    private final String TOKEN_AUDIENCE = "xit.gateway";
    private final String TOKEN_ISSUER = "torch-game-gateway";
    private final String TOKEN_SUBJECT = "user";

    public DefaultAuthTokenHandler(){
        this.secretKey = getSecretKey();
        this.parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .requireAudience(TOKEN_AUDIENCE)
                .requireIssuer(TOKEN_ISSUER)
                .requireSubject(TOKEN_SUBJECT)
                .build();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    @Override
    public String newToken(Long userId) {
        return Jwts.builder()
                .setId(userId.toString())
                .signWith(secretKey)
                .setAudience(TOKEN_AUDIENCE)
                .setIssuer(TOKEN_ISSUER)
                .setSubject(TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + 100000*60*60))
                .compact();
    }

    @Override
    public AuthToken parse(String token) {
        Claims body = parser.parseClaimsJws(token).getBody();

        return new AuthToken(
                Long.parseLong(body.getId()),
                body.getSubject(),
                body.getAudience(),
                body.getIssuer(),
                body.getExpiration()
        );
    }


}
