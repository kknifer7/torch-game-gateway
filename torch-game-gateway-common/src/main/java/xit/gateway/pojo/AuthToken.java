package xit.gateway.pojo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private Long id;
    private String subject;
    private String audience;
    private String issuer;
    private Date expiration;
}
