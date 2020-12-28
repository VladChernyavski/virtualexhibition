package by.cniitu.virtualexhibition.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Objects;

public class JwtsUtil {

    public static Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return claims;
    }

    public static String generateTokenForUser(String data) {
        return Jwts.builder()
                .setSubject(data)
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes())
                .compact();
    }

    public static String getUserData(String token) {
        Claims claims = JwtsUtil.getClaims(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        return claims.getSubject();
    }

}
