package xit.gateway.core.filter;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xit.gateway.api.jwt.AuthTokenHandler;
import xit.gateway.constant.RedisKey;
import xit.gateway.pojo.AuthToken;
import xit.gateway.pojo.UserWithAuths;
import xit.gateway.utils.GatewayUriUtils;
import xit.gateway.utils.RedisUtils;

import java.util.List;

@Component
public class AuthenticationFilter implements WebFilter {
    private final AuthTokenHandler handler;

    @Autowired
    public AuthenticationFilter(AuthTokenHandler handler) {
        this.handler = handler;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (GatewayUriUtils.matchLogin(path)){
            return chain.filter(exchange);
        }

        List<String> header = exchange.getRequest().getHeaders().get("AUTH-TOKEN");
        String tokenStr;
        AuthToken token;
        UserWithAuths user;

        if (header == null || header.size() != 1){
            // 请求头没有token
            return chain.filter(exchange);
        }
        tokenStr = header.get(0);
        try{
            token = handler.parse(tokenStr);
        }catch (JwtException | IllegalArgumentException e){
            // token解析失败
            return chain.filter(exchange);
        }
        user = RedisUtils.get(RedisKey.USER.extend(token.getId()), UserWithAuths.class);
        if (user == null){
            // token过期
            return chain.filter(exchange);
        }
        handleAuthenticationToService(path, user);

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(getAuthentication(user, tokenStr)));
    }

    // 处理去往服务代理的请求（如果用户持有目标服务的权限，向其权限列表中添加“dynamic”权限，让其能够通过Spring Security的权限校验）
    private void handleAuthenticationToService(String path, UserWithAuths user){
        if (GatewayUriUtils.isUsingService(path) && userHasAuthToService(user, GatewayUriUtils.getServiceNameFromUri(path))){
            user.getAuths().add("dynamic");
        }
    }

    private Authentication getAuthentication(UserWithAuths user, Object credentials){
        List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(user.getAuths().toArray(new String[0]));
        User securityUser = new User(user.getUsername(), "", auths);

        return new UsernamePasswordAuthenticationToken(securityUser, credentials, auths);
    }

    // 判断用户是否有权限访问服务
    private boolean userHasAuthToService(UserWithAuths user, String serviceName){
        return user.getAuths().contains(serviceName);
    }
}
