package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken;

import com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken.RefreshTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    private final RefreshTokenService refreshTokenService;

    private String jwtSecret = "5ddb737cea23d62658b3865ce51888da8732f5cd9c32b8433dd0c4214f5527c5b1d31aaa58286da0db44a507e41962fbd7054df6ffd327388b3c8c3762031082";
    private int jwtExpirationMs = 3600000;
    private SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Environment env = getEnvironment();
            if (null != env) {
                String jwt = Jwts.builder()
                        .issuer("PMS")
                        .subject("JWT_Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

                response.setHeader("Authorization", jwt);
                createRefreshToken(authentication.getName());
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println(request.getServletPath());
        return !request.getServletPath().equals("/users/login");
    }

    //Refresh Token:
    protected void createRefreshToken(String username) {
        refreshTokenService.createRefreshToken(username);
    }
}
