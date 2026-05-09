package com.reviewsite.reviewsite_api.config;

import com.reviewsite.reviewsite_api.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(org.springframework.http.HttpMethod.GET,
                                        "/api/reviews/**").hasRole("REVIEWER")
                                .requestMatchers(org.springframework.http.HttpMethod.GET,
                                        "/api/shows/**").permitAll()
                                .requestMatchers(org.springframework.http.HttpMethod.POST,
                                        "/api/reviews/**").hasRole("REVIEWER")
                                .requestMatchers(org.springframework.http.HttpMethod.POST,
                                        "/api/shows/**").hasRole("ADMIN")
                                .requestMatchers(org.springframework.http.HttpMethod.DELETE,
                                        "/api/reviews/**").hasRole("REVIEWER")
                                .requestMatchers(org.springframework.http.HttpMethod.DELETE,
                                        "/api/shows/**").hasRole("ADMIN")
                                .requestMatchers(org.springframework.http.HttpMethod.PATCH,
                                        "/api/reviews/**").hasRole("REVIEWER")
                                .requestMatchers(org.springframework.http.HttpMethod.PATCH,
                                        "/api/shows/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        }))
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}