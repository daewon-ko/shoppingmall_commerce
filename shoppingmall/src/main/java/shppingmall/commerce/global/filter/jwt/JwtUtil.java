package shppingmall.commerce.global.filter.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import shppingmall.commerce.global.config.jwt.JwtProperties;
import shppingmall.commerce.user.entity.UserRole;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.secretKey  = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public Long getId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("email", String.class);
    }

    public UserRole getRole(String token) {
        String role = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return UserRole.valueOf(role);
    }

    public boolean isExpired(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        } catch (Exception ex) {
            return true;
        }
    }

    public String createJwt(Long id, String email, UserRole role, long expiredMs) {
        return Jwts
                .builder()
                .claim("id", id)
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }


}
