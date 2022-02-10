package com.skt.shms.shmsauthservice.dto.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    private Timestamp accessTokenExpireDate;
    private Timestamp refreshTokenExpireDate;
}
