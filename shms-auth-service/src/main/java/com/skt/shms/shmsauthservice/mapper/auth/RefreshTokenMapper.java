package com.skt.shms.shmsauthservice.mapper.auth;

import java.util.Optional;
import com.skt.shms.shmsauthservice.dto.auth.jwt.RefreshTokenRequestDto;
import com.skt.shms.shmsauthservice.dto.auth.jwt.RefreshTokenResponseDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
	
	public Optional<RefreshTokenResponseDto> findRefreshTokenByUserId(String userId);
	public int findNextNoByUserId(String userId);

	public int updateExpire(RefreshTokenRequestDto refreshTokenReq);
	public int insert(RefreshTokenRequestDto refreshTokenReq);


	public Optional<RefreshTokenResponseDto> findByRefreshToken(RefreshTokenRequestDto refreshTokenReq);
	public int update(RefreshTokenRequestDto refreshTokenReq);
	public int delete(RefreshTokenRequestDto refreshTokenReq);
}
