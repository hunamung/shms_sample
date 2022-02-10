package com.skt.shms.shmsauthservice.service.user;

import com.skt.shms.shmsauthservice.advice.exception.CUserNotFoundException;
import com.skt.shms.shmsauthservice.dto.user.UserResponseDto;
import com.skt.shms.shmsauthservice.mapper.user.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponseDto findByUserId(String userId) {
        UserResponseDto user = userMapper.findByUserId(userId)
                .orElseThrow(CUserNotFoundException::new);

        return user;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userMapper.findAllUser();
    }
}
