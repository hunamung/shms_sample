package com.skt.shms.shmsauthservice.dto.auth.join;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoinRequestDto {
    private String userId;
    private String email;
    private String password;
    private String name;
    private String nickName;

    public UserJoinRequestDto toEntity(PasswordEncoder passwordEncoder) {
        return UserJoinRequestDto.builder()
                .userId(userId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .build();
    }

    public UserJoinRequestDto toEntity() {
        return UserJoinRequestDto.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
