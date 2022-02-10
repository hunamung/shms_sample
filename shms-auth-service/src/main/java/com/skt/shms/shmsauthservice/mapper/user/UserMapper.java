package com.skt.shms.shmsauthservice.mapper.user;

import java.util.List;
import java.util.Optional;

import com.skt.shms.shmsauthservice.dto.auth.join.UserJoinRequestDto;
import com.skt.shms.shmsauthservice.dto.user.UserResponseDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	public Optional<UserResponseDto> findByUserId(String userId);

	public List<UserResponseDto> findAllUser();
	
	public int insert(UserJoinRequestDto userJoin);
}
