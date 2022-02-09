package com.skt.shms.shmsauthservice.mapper.user;

import java.util.Optional;
import com.skt.shms.shmsauthservice.dto.user.UserRResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	public Optional<UserRResponseDto> findById(long id);
	
}
