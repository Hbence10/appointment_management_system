package com.Hbence.appointmentManagementAPI.configurations.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                //CORS settings: (CORS - )
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));        //A tamogatott origineket adjuk meg
                        config.setAllowedMethods(Collections.singletonList("*"));                            //A tamogatott http verbeket adjuk meg
                        config.setAllowCredentials(true);                                                    //A cookiekat fogadjuk
                        config.setAllowedHeaders(Collections.singletonList("*"));                            //A http headerek adjuk meg
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))

                //Jogosultsagok:
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/devices/addCategory", "/devices/deleteCategory/**", "/devices/updateCategory").hasAnyRole("admin", "superAdmin")     //deviceController
                        .requestMatchers("rule/update", "gallery/update").hasAnyRole("admin", "superAdmin")                                                     //otherStuffController
                        .requestMatchers("/news/addNews", "/news/update", "/news/delete**").hasAnyRole("admin", "superAdmin")                                   //newsController
                        .requestMatchers("/reservation/user/**", "/reservation/reservedDates", "/reservation/reservedHours", "/reservation/date/**", "/reservation/makeReservation", "/reservation/cancel/**", "/reservation/getReservationType", "/reservation/paymentMethods", "/reservation/phoneCodes").permitAll() //reservationController
                        .requestMatchers("/reservation/addReservationType", "/reservation/deleteReservation/**", "/reservation/updateReservationType").hasAnyRole("admin", "superAdmin") //reservationController
                        .requestMatchers("/reviews/addReview", "/reviews/addLike", "/reviews/changeLikeType/**").hasAnyRole("user", "admin", "superAdmin")
                        .requestMatchers("/reviews/deleteReview/**", "reviews/update", "/users/updateUser", "users/deleteUser/**").hasAnyRole("user")
                        .requestMatchers("/reviews/getAll", "/rule", "/gallery", "/users/login", "/users/register", "/users/passwordReset", "/users/verificationCode", "/news/getAll", "/devices/getAllCategory").permitAll()
                )

                //CSRF settings:
//                .csrf(csrfConfig -> csrfConfig
//                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults());

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

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
