package com.Hbence.appointmentManagementAPI.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.function.Supplier;

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
                .authorizeHttpRequests((requests) -> requests.anyRequest().permitAll()
//                        .requestMatchers("").authenticated()                                       //Azok az endpointok amelyekhez szukseges lesz az authentikacio
//                        .requestMatchers("").permitAll()                                           //Azok az endpointok amelyekhez nem lesz szukseges az authentikacio
                )

                //CSRF settings:
//                .csrf(csrfConfig -> csrfConfig
//                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                .formLogin(config -> config.disable());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
