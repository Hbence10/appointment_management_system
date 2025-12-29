package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String createRefreshToken(String username) {
        Users searchedUser = userRepository.findByUsername(username).orElse(null);
        if (searchedUser != null) {
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setUser(searchedUser);
            newRefreshToken.setExpiryDate(Instant.now().plusMillis(86400000));
            newRefreshToken.setToken(UUID.randomUUID().toString());
            return refreshTokenRepository.save(newRefreshToken).getToken();
        }
        return "";
    }

    public Boolean isTokenExpired(String token) {
        RefreshToken searchedToken = refreshTokenRepository.findByToken(token).orElse(null);
        if (searchedToken == null) {
            return false;
        } else {
            if (searchedToken.getExpiryDate().isBefore(Instant.now())) {
                refreshTokenRepository.delete(searchedToken);
                return true;
            } else {
                return false;
            }
        }
    }

    public Users getUserFromToken(String token) {
        Long userId = userRepository.getUserIdByToken(token).orElse(null);
        if (userId != null) {
            return userRepository.findById(userId).get();
        }

        return null;
    }
}