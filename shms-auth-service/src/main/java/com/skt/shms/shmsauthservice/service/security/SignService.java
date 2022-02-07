package com.skt.shms.shmsauthservice.service.security;

import com.skt.shms.shmsauthservice.advice.exception.*;
import com.skt.shms.shmsauthservice.config.security.JwtProvider;
import com.skt.shms.shmsauthservice.domain.security.RefreshToken;
import com.skt.shms.shmsauthservice.domain.security.RefreshTokenJpaRepo;
import com.skt.shms.shmsauthservice.domain.user.User;
import com.skt.shms.shmsauthservice.domain.user.UserJpaRepo;
import com.skt.shms.shmsauthservice.dto.jwt.TokenDto;
import com.skt.shms.shmsauthservice.dto.jwt.TokenRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserLoginRequestDto;
import com.skt.shms.shmsauthservice.dto.sign.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {

        // 회원 정보 존재하는지 확인
        User user = userJpaRepo.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);

        // 회원 패스워드 일치 여부 확인
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        // AccessToken, RefreshToken 발급
        TokenDto tokenDto = jwtProvider.createTokenDto(user.getUserId(), user.getRoles());

        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.getUserId())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        if (userJpaRepo.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new CEmailSignupFailedException();
        return userJpaRepo.save(userSignupDto.toEntity(passwordEncoder)).getUserId();
    }

    @Transactional
    public Long socialSignup(UserSignupRequestDto userSignupRequestDto) {
        if (userJpaRepo
                .findByEmailAndProvider(userSignupRequestDto.getEmail(), userSignupRequestDto.getProvider())
                .isPresent()
        ) throw new CUserExistException();
        return userJpaRepo.save(userSignupRequestDto.toEntity()).getUserId();
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 만료된 refresh token 에러
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // user pk로 유저 검색 / repo 에 저장된 Refresh Token 이 없음
        User user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken = tokenJpaRepo.findByKey(user.getUserId())
                .orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtProvider.createTokenDto(user.getUserId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }
}
