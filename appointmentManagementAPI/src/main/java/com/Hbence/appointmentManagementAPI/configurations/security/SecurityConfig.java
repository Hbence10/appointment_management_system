package com.Hbence.appointmentManagementAPI.configurations.security;

import com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.JWTTokenGeneratorFilter;
import com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //CORS settings: (CORS - Cross Origin Resource Sharing)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));        //A tamogatott origineket adjuk meg
                        config.setAllowedMethods(Collections.singletonList("*"));                            //A tamogatott http verbeket adjuk meg
                        config.setAllowCredentials(true);                                                    //A cookiekat fogadjuk
                        config.setAllowedHeaders(Collections.singletonList("*"));                            //A http headerek adjuk meg
                        config.setExposedHeaders(Arrays.asList("Authorization", "Access-Control-Expose-Headers"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/devices/addCategory", "/devices/deleteCategory/**", "/devices/updateCategory", "/devices/update", "/devices/addDevice", "/devices/delete/**").hasAnyRole("admin", "superAdmin")
                        .requestMatchers("rule/update", "gallery/update").hasAnyRole("admin", "superAdmin")
                        .requestMatchers("/news/addNews", "/news/update", "/news/delete/**").hasAnyRole("admin", "superAdmin")
                        .requestMatchers("/reservation/reservedDates", "/reservation/reservedHours", "/reservation/date/**", "/reservation/makeReservation", "/reservation/cancel/**", "/reservation/getReservationType", "/reservation/paymentMethods", "/reservation/phoneCodes").permitAll() //reservationController
                        .requestMatchers("/reservation/addReservationType", "/reservation/deleteReservation/**", "/reservation/updateReservationType").hasAnyRole("admin", "superAdmin")
                        .requestMatchers("/reviews/addReview", "/reviews/addLike", "/reviews/changeLikeType/**", "/reservation/user/**").hasAnyRole("user", "admin", "superAdmin")
                        .requestMatchers("/reviews/deleteReview/**", "reviews/update", "/users/updateUser", "/reviews/addLike", "/reviews/changeLikeType/**", "users/deleteUser/**").hasRole("user")
                        .requestMatchers("/reservation/asd", "/reviews/getAll", "/rule", "/gallery", "/users/login", "/users/register", "/users/getVerificationCode", "/users/checkVerificationCode", "/users/passwordReset", "/news/getAll", "/devices/getAllCategory").permitAll()
                )

                //CSRF settings (CSRF - Cross Site Request Forgery):
//                .csrf(csrfConfig -> csrfConfig
//                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))
                .formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //
    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    //
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
