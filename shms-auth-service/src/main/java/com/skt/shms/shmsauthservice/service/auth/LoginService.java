package com.skt.shms.shmsauthservice.service.auth;

import com.skt.shms.shmsauthservice.advice.exception.*;
import com.skt.shms.shmsauthservice.config.jwt.JwtProvider;
// import com.skt.shms.shmsauthservice.domain.jwt.RefreshToken;
// import com.skt.shms.shmsauthservice.domain.jwt.RefreshTokenJpaRepo;
import com.skt.shms.shmsauthservice.dto.auth.join.UserJoinRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.RefreshTokenRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.RefreshTokenResponseDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.TokenRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.TokenResponseDto;
import com.skt.shms.shmsauthservice.dto.auth.login.UserLoginRequestDto;
import com.skt.shms.shmsauthservice.dto.user.UserResponseDto;
import com.skt.shms.shmsauthservice.mapper.auth.RefreshTokenMapper;
import com.skt.shms.shmsauthservice.mapper.user.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import io.jsonwebtoken.Claims;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    
    private final UserMapper userMapper;
    private final RefreshTokenMapper refreshTokenMapper;

    @Transactional
    public int join(UserJoinRequestDto userJoinDto) {
        if (userMapper.findByUserId(userJoinDto.getUserId()).isPresent()){
            throw new CUserIdJoinFailedException();
        }

        return userMapper.insert(userJoinDto.toEntity(passwordEncoder));
    }

    @Transactional
    public TokenResponseDto login(UserLoginRequestDto userLoginDto) {

        // 회원 정보 존재하는지 확인
        UserResponseDto userDto = userMapper.findByUserId(userLoginDto.getUserId())
            .orElseThrow(CUserIdLoginFailedException::new);

        // 회원 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(userLoginDto.getPassword(), userDto.getPassword()))
            throw new CUserIdLoginFailedException();

        // 저장된 RefreshToken 사용자 최근항목 호출
        Integer maxNo = refreshTokenMapper.findNextNoByUserId(userDto.getUserId());

        // AccessToken, RefreshToken 발급
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");

        TokenResponseDto tokenResponseDto = jwtProvider.createTokenDto(userDto.getUserId(), roles);

        RefreshTokenRequestDto refreshTokenDto = RefreshTokenRequestDto.builder()
            .no(maxNo)
            .userId(userDto.getUserId())
            .token(tokenResponseDto.getRefreshToken())
            .expireDate(tokenResponseDto.getRefreshTokenExpireDate())
            .build();
        
        //refreshTokenMapper.delete(refreshTokenDto);
        refreshTokenMapper.updateExpire(refreshTokenDto);
        refreshTokenMapper.insert(refreshTokenDto);

        return tokenResponseDto;
    }

    @Transactional
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 만료된 refresh token 에러
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // 사용자 아이디로 유저 검색 / repo 에 저장된 Refresh Token 이 없으면 오류발생
        RefreshTokenResponseDto refreshToken = refreshTokenMapper.findRefreshTokenByUserId(tokenRequestDto.getUserId())
            .orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");

        // AccessToken 토큰 재발급
        TokenResponseDto newCreatedToken = jwtProvider.createAccessTokenDto(tokenRequestDto.getUserId(), roles);

        return newCreatedToken;
    }

    @Transactional
    public TokenResponseDto reissue_v2(TokenRequestDto tokenRequestDto) {
        // 만료된 refresh token 에러
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken 에서 사용자 아이디 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Claims claims = jwtProvider.getAuthentication(accessToken);

        String userId = claims.getSubject();
        List<String> roles = (List<String>) claims.get("roles");

        // 사용자 아이디로 유저 검색 / repo 에 저장된 Refresh Token 이 없으면 오류발생
        Integer currentNo = refreshTokenMapper.findNextNoByUserId(userId) - 1;
        if(currentNo == 0){
            throw new CRefreshTokenException();
        }

        RefreshTokenRequestDto requestDto = RefreshTokenRequestDto.builder().no(currentNo).userId(userId).build();
        RefreshTokenResponseDto refreshToken = refreshTokenMapper.findByRefreshToken(requestDto)
                .orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenResponseDto newCreatedToken = jwtProvider.createTokenDto(userId, roles);

        RefreshTokenRequestDto refreshTokenDto = RefreshTokenRequestDto.builder()
            .no(currentNo + 1)
            .userId(userId)
            .token(newCreatedToken.getRefreshToken())
            .expireDate(newCreatedToken.getRefreshTokenExpireDate())
            .build();
        
        refreshTokenMapper.updateExpire(refreshTokenDto);
        refreshTokenMapper.insert(refreshTokenDto);

        return newCreatedToken;
    }

    @Transactional
    public int logout(UserLoginRequestDto userLoginDto) {
        
        // 회원 정보 존재하는지 확인
        UserResponseDto userDto = userMapper.findByUserId(userLoginDto.getUserId())
            .orElseThrow(CUserIdLoginFailedException::new);

        RefreshTokenRequestDto refreshTokenDto = RefreshTokenRequestDto.builder()
            .userId(userDto.getUserId())
            .build();
        
        //return refreshTokenMapper.delete(refreshTokenDto);
        return refreshTokenMapper.updateExpire(refreshTokenDto);
    }
}
