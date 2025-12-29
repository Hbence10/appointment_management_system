package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Procedure(name = "getRefreshTokenByUserId", procedureName = "getRefreshTokenByUserId")
    Optional<RefreshToken> getRefreshTokenByUserId(@Param("userIdIN") Long userId);
}
