package com.skt.shms.shmsauthservice.domain.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByKey(Long key);
}
