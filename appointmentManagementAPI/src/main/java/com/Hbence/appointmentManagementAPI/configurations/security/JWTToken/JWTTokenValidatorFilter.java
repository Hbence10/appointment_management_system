package com.Hbence.appointmentManagementAPI.configurations.security.JWTToken;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private String jwtSecret = "5ddb737cea23d62658b3865ce51888da8732f5cd9c32b8433dd0c4214f5527c5b1d31aaa58286da0db44a507e41962fbd7054df6ffd327388b3c8c3762031082";
    private int jwtExpirationMs = 3600000;
    private SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (null != jwt && validateJwtToken(jwt)) {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
            String username = claims.get("username").toString();
            String authorities = claims.get("authorities").toString();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        ArrayList<String> shouldNotFilterPaths = new ArrayList<>(Arrays.asList("/reservation/reservedDate","/reservation/reservedDates", "/reservation/reservedHours", "/reservation/date/**", "/reservation/cancel/**", "/reservation/getByEmailAndVCode", "/reservationStuff/getReservationType", "/reservationStuff/paymentMethods", "/reservationStuff/phoneCodes", "/reviews/getAll", "/rule", "/gallery", "/news/getAll", "/devices/getAllCategory", "/users/login", "/users/register", "/users/getVerificationCode", "/users/checkVerificationCode", "/users/passwordReset", "/users/**", "/reservation/makeReservation", "/details", "/openingDetails"));
        return shouldNotFilterPaths.contains(request.getServletPath());
    }


    //
    public boolean validateJwtToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            //ha lejart akkor van a refresh token

            System.out.println("JWT token is expired: " + e.getMessage());


        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
