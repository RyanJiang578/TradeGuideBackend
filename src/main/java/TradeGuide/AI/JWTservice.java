package TradeGuide.AI;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTservice {
private final SecretKey key;
    public JWTservice(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date(now))
            .expiration(new Date(now + 3600000))
            .signWith(key)
            .compact();
    }

    public String validateToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }
}
