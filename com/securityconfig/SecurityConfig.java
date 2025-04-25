package com.example.com.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/otp/send").permitAll()
                        .requestMatchers("/api/otp/validate").permitAll()
                        .requestMatchers("/api/drivers/**").permitAll()
                        .requestMatchers("/api/rides/book").permitAll()
                        .requestMatchers("/api/rides/**").permitAll()
                        .requestMatchers("/api/preferences/**").permitAll()
                        .requestMatchers("/api/notifications/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/alerts/**").permitAll()
                        .requestMatchers("/api/bookings/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
