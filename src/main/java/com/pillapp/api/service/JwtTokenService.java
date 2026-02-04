package com.pillapp.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;


@Service
public class JwtTokenService {

    public String generateToken(String username) {
        return Jwts.builder()
                .setId(toTokenId(username))
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
                .signWith(getKey())
                .compact();
    }

    private Jws<Claims> parseJwt(String jwtString) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwtString);
    }

    public boolean isValidToken(String jwtString) {
        try {
            parseJwt(jwtString);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public String getUsername(String jwtString) {
        var jwt = parseJwt(jwtString);
        var id = jwt.getBody().getId();
        return fromTokenId(id);
    }

    // -----
    // Memorize usernames for each token produced
    private final List<String> usernames = new ArrayList<>();

    private String toTokenId(String username) {
        var index = usernames.size();
        usernames.add(username);
        return Integer.toHexString(index);
    }

    private String fromTokenId(String tokenId) {
        var index = (int) Long.parseLong(tokenId, 16);
        if (index >= usernames.size()) {
            return null;
        }
        return usernames.get(index);
    }

    // Use secret from environment variable
    // If not present, use the default secret for dev environment
    @Value("${jwt-secret}")
    private String secret;
    private SecretKeySpec key;

    private Key getKey() {
        if (key == null) {
            key = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
        }
        return key;
    }
}
