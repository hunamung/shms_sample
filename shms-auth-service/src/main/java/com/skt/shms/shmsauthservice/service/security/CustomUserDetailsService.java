package com.skt.shms.shmsauthservice.service.security;

import com.skt.shms.shmsauthservice.advice.exception.CUserNotFoundException;
import com.skt.shms.shmsauthservice.domain.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userJpaRepo.findById(Long.parseLong(userPk))
                .orElseThrow(CUserNotFoundException::new);
    }
}
