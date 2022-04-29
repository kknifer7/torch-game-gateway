package xit.gateway.core.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;
import xit.gateway.constant.ResultCode;
import xit.gateway.core.filter.AuthenticationFilter;
import xit.gateway.core.filter.LimitingFilter;
import xit.gateway.core.service.impl.UserServiceImpl;
import xit.gateway.pojo.ResultInfo;
import xit.gateway.utils.JsonUtils;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    private final AuthenticationFilter authenticationFilter;
    private final LimitingFilter limitingFilter;

    @Autowired
    public WebSecurityConfig(AuthenticationFilter authenticationFilter, LimitingFilter limitingFilter) {
        this.authenticationFilter = authenticationFilter;
        this.limitingFilter = limitingFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager reactiveAuthenticationManager){
        return http
                .addFilterAt(limitingFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/action/admin/**").hasAuthority("admin")
                .pathMatchers("/service/**").hasAuthority("dynamic")
                .and()
                .authenticationManager(reactiveAuthenticationManager)
                .csrf()
                .disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling()
                .accessDeniedHandler((exchange, denied) -> {
                    ServerHttpResponse response = exchange.getResponse();
                    HttpHeaders headers = response.getHeaders();

                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    return response.writeWith(
                            Mono.just(response.bufferFactory()
                                    .wrap(JsonUtils.object2String(
                                            new ResultInfo<>(ResultCode.FORBIDDEN.getValue(),
                                                    "没有访问权限",
                                                    null
                                            )).getBytes(StandardCharsets.UTF_8))
                            )
                    );
                })
                .authenticationEntryPoint(((exchange, ex) -> {
                    ServerHttpResponse response = exchange.getResponse();
                    HttpHeaders headers = response.getHeaders();

                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    return response.writeWith(
                            Mono.just(response.bufferFactory()
                                    .wrap(JsonUtils.object2String(
                                            new ResultInfo<>(ResultCode.FORBIDDEN.getValue(),
                                                    "请登录",
                                                    null
                                            )).getBytes(StandardCharsets.UTF_8)
                            )
                    ));
                }))
                .and()
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(UserServiceImpl userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);

        authenticationManager.setPasswordEncoder(passwordEncoder);

        return authenticationManager;
    }
}
