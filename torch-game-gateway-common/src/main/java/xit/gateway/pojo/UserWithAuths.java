package xit.gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

// 带有服务访问权限列表的User
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithAuths {
    private Long id;
    private String username;
    private String pwd;
    private Set<String> auths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
