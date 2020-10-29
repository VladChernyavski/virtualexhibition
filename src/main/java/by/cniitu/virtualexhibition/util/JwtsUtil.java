package by.cniitu.virtualexhibition.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtsUtil {

    public static Claims getClaims(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return claims;
    }

}
