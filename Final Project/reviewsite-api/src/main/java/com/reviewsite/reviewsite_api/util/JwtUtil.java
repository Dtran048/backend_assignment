package com.reviewsite.reviewsite_api.util;

import com.reviewsite.reviewsite_api.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Autowired
    private UserRepository userRepo;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration; // milliseconds from application.properties
    // Build a SecretKey from the string secret
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    // ── Generate a token for a user ─────────────────────────────────
    public String generateToken(UserDetails userDetails, String email) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // sub claim
                .claim("email", email )
                .issuedAt(new Date()) // iat claim
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey()) // sign with HS256
                .compact(); // build the string
    }
    // ── Extract username (sub claim) from token ──────────────────────
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    // ── Validate token: signature + expiry ───────────────────────────
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
    // ── Private helpers ──────────────────────────────────────────────
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // verify signature
                .build()
                .parseSignedClaims(token).getPayload();
    }
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}