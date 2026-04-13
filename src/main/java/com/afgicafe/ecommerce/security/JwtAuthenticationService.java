package com.afgicafe.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtAuthenticationService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    @Value("${application.security.jwt.issuer}")
    private String issuer;

    private SecretKey signingKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // CLAIM EXTRACTION
    public String extractUsername (String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExpiration (String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(jwt));
    }

    // TOKEN GENERATION
    public String generateAccessToken (UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken (Map<String, Object> extraClaims, UserDetails userDetails){
        extraClaims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .issuer(issuer)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    // TOKEN VALIDATION
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        try {
            final String username = extractUsername(jwt);

            return username.equals(userDetails.getUsername())
                    && !isTokenExpired(jwt)
                    && userDetails.isAccountNonExpired()
                    && userDetails.isEnabled()
                    && userDetails.isAccountNonLocked()
                    && userDetails.isCredentialsNonExpired();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    // INTERNAL PARSING
    private Claims extractAllClaims(String jwt) {
        try{
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (Exception e) {
            throw  new IllegalArgumentException("Invalid JWT token");
        }
    }
}
