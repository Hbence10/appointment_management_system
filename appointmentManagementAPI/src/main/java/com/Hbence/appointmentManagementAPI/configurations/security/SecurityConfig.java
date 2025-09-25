package com.Hbence.appointmentManagementAPI.configurations.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
//                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
//                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                //CORS settings:
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
                .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll())

                //CSRF settings:
//                .csrf(csrfConfig -> csrfConfig
//                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .formLogin(config -> config.disable());

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 1 << 12, 3);
    }

    /*
     * Argon2 Parameterek:
     *           - iterations --> annak a szama, hogy a jelszo hanyszor lett hashelve
     *           - memoryCost --> az a mennyiseg amit az Argon2 fog hasznalni
     *           - parallelism --> number of threads
     * */
}
