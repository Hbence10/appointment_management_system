package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public void createRefreshToken(String username) {
        Users searchedUser = userRepository.findByUsername(username).orElse(null);
        if (searchedUser != null) {
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setUser(searchedUser);
            newRefreshToken.setExpiryDate(Instant.now().plusMillis(86400000));
            newRefreshToken.setToken(UUID.randomUUID().toString());
            refreshTokenRepository.save(newRefreshToken);
        }
    }
}
