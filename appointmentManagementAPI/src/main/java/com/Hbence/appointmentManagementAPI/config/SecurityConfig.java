package com.Hbence.appointmentManagementAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        http.csrf(csrfConfig -> csrfConfig.disable());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {   //Az alapertelmezett titkositas bycrypt lesz
////        return new BCryptPasswordEncoder();  //specifikusan hozz letre PasswordEncoder-t
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
}
