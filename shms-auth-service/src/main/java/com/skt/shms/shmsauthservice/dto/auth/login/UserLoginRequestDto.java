package com.skt.shms.shmsauthservice.dto.auth.login;

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
public class UserLoginRequestDto {
    private String userId;
    private String email;
    private String password;

    public UserLoginRequestDto toEntity(PasswordEncoder passwordEncoder) {
        return UserLoginRequestDto.builder()
                .userId(userId)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
