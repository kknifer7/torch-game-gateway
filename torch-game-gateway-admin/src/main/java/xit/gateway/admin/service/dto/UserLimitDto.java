package xit.gateway.admin.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserLimitDto implements Serializable {

    private int userId;
    private int limit;
    private int limitingTimeout;
    private String limitingTimeUnit;

}
