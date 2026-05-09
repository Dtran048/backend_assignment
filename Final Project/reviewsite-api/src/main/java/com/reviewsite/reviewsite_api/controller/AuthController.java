package com.reviewsite.reviewsite_api.controller;

import com.reviewsite.reviewsite_api.entity.User;
import com.reviewsite.reviewsite_api.repository.UserRepository;
import com.reviewsite.reviewsite_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null ) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "404", "message", "Missing required fields!","timestamps", new Date()));
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409)
                    .body(Map.of("status", "409", "message", "Username already in use","timestamps", new Date()));
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(409)
                    .body(Map.of("status", "409", "message", "Email already in use","timestamps", new Date()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("REVIEWER"));
        }
        userRepository.save(user);
        return ResponseEntity.status(201)
                .body(Map.of("message", "User registered successfully"));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
         try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRepository.findByEmail(loginRequest.getEmail()).getUsername(),
                            loginRequest.getPassword()
                    )
            );
         } catch (NullPointerException e) {
             return ResponseEntity.status(401)
                     .body(Map.of("status", "401", "message", "Invaild Credentials ","timestamps", new Date()));
         } catch (Exception e) {
             return ResponseEntity.status(401)
                     .body(Map.of("status", "401", "message", "Invaild Credentials ","timestamps", new Date()));
         }
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userRepository.findByEmail(loginRequest.getEmail()).getUsername());
        String token = jwtUtil.generateToken(userDetails, loginRequest.getId());
        return ResponseEntity.ok(Map.of("token", token));
    }
}