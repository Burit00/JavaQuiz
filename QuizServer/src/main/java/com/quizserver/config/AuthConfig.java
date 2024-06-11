package com.quizserver.config;

import com.quizserver.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.quizserver.config.auth.SecurityFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/quiz").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/quiz/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/quiz").hasRole(UserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/quiz/*").hasRole(UserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/quiz/*").hasRole(UserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.GET, "/api/v1/quiz/*/updateForm").hasRole(UserRole.ADMIN.getValue())
                        .requestMatchers(HttpMethod.POST, "/api/v1/quiz/*/calculateScore").hasRole(UserRole.USER.getValue())
                        .requestMatchers(HttpMethod.GET, "/api/v1/question").hasRole(UserRole.USER.getValue())
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}