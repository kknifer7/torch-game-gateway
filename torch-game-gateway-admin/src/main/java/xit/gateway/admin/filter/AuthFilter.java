package xit.gateway.admin.filter;

import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xit.gateway.api.jwt.AuthTokenHandler;
import xit.gateway.constant.RedisKey;
import xit.gateway.constant.ResultCode;
import xit.gateway.exception.user.TokenExpirationException;
import xit.gateway.pojo.AuthToken;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.pojo.UserWithAuths;
import xit.gateway.utils.JsonUtils;
import xit.gateway.utils.RIUtils;
import xit.gateway.utils.RedisUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@SuppressWarnings("all")
public class AuthFilter extends OncePerRequestFilter {
    private final AuthTokenHandler tokenHandler;

    @Autowired
    public AuthFilter(AuthTokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (String.valueOf(request.getRequestURL()).contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("AUTH-TOKEN");
        AuthToken authToken = null;

        if (StringUtils.isNotBlank(token)) {
            try {
                authToken = tokenHandler.parse(token);
            }catch (IllegalArgumentException | JwtException e){
                response.setStatus(403);
                this.send(response, ResultCode.FORBIDDEN, "token不合法", null);
                return;
            }
            UserWithAuths user = RedisUtils.get(RedisKey.USER.extend(authToken.getId()), UserWithAuths.class);

            if (user == null){
                response.setStatus(401);
                this.send(response, ResultCode.FORBIDDEN, "token已过期", null);
            }else{
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                String[] authArr = user.getAuths().toArray(new String[0]);
                UserDetails ud = User.withUsername(user.getUsername())
                        .authorities(authArr)
                        .password("")
                        .build();

                context.setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                ud,
                                token,
                                AuthorityUtils.createAuthorityList(authArr)
                        ));
                SecurityContextHolder.setContext(context);
                filterChain.doFilter(request, response);
            }
        }else {
            this.send(response, ResultCode.FORBIDDEN, "token不能为空", null);
        }
    }

    private <T> void send(HttpServletResponse response, ResultCode code, String msg, T data){
        try {
            response.getWriter().print(JsonUtils.object2String(new ResultInfo<>(code.getValue(), msg, data)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
