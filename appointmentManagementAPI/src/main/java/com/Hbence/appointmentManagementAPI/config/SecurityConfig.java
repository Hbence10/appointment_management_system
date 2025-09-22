package com.Hbence.appointmentManagementAPI.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
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
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("").authenticated()                                       //Azok az endpointok amelyekhez szukseges lesz az authentikacio
//                        .requestMatchers("").permitAll()                                           //Azok az endpointok amelyekhez nem lesz szukseges az authentikacio
                                .anyRequest().permitAll()
                )
                .formLogin(config -> config.disable());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
