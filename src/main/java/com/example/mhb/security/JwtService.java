package com.example.mhb.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import com.example.mhb.config.JwtProperties;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties props;
    private final SecretKey currentKey;
    private final SecretKey previousKey;

    public JwtService(JwtProperties props) {
        this.props = props;

        // Current key (always exists)
        this.currentKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(props.getCurrentSecret())
        );

        // Previous key (may be null on first start)
        String prev = props.getPreviousSecret();
        this.previousKey = (prev != null && !prev.isBlank())
                ? Keys.hmacShaKeyFor(Decoders.BASE64.decode(prev))
                : null;
    }

    public String generateAccessToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + props.getAccessTokenExpirationMs())) // 10 minutes
                .signWith(currentKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + props.getRefreshTokenExpirationMs())) // 30 days
                .signWith(currentKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseWithFallback(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String extracted = extractUsername(token);
            return extracted.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return parseWithFallback(token).getBody().getExpiration().before(new Date());
    }

    private Jws<Claims> parseWithFallback(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(currentKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            if (previousKey != null) {
                return Jwts.parserBuilder()
                        .setSigningKey(previousKey)
                        .build()
                        .parseClaimsJws(token);
            }
            throw e;
        }
    }
}