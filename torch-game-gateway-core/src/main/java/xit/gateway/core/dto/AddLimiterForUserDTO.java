package xit.gateway.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddLimiterForUserDTO {
    private Long userId;
    private Long limit;
    private Long limitingTimeout;
    private TimeUnit limitingTimeUnit;
}
