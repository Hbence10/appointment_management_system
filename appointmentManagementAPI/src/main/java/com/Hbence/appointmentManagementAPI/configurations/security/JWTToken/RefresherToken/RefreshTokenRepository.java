package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
