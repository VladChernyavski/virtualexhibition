package by.cniitu.virtualexhibition.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    // Кодовое слово для расшифровки
    private static final String secretKeyToGenJWTs = "SecretKeyToGenJWTs";

    public String generateToken(String login, String password) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setSubject(password)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secretKeyToGenJWTs)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKeyToGenJWTs).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKeyToGenJWTs).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
