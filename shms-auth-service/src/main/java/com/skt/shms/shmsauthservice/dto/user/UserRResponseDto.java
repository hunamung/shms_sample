package com.skt.shms.shmsauthservice.dto.user;

import com.skt.shms.shmsauthservice.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class UserRResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String nickName;
    private LocalDateTime modifiedDate;
}
