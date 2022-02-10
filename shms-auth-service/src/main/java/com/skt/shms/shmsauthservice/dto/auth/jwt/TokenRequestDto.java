package com.skt.shms.shmsauthservice.dto.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequestDto {
    String userId;
    String accessToken;
    String refreshToken;
}
