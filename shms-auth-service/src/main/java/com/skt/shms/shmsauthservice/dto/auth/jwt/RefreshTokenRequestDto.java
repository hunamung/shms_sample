package com.skt.shms.shmsauthservice.dto.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {
    Integer no;
    String userId;
    String token;
    Timestamp expireDate;
}
