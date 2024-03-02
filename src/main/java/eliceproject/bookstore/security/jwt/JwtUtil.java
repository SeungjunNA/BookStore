package eliceproject.bookstore.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {

    @Value("${jwt.secret}")
    private static String secretKey;

    public static String createToken(String username, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String getUsername(String token){
        return extractClaims(token).get("username").toString();
    }

    public static boolean isExpired(String token){
        Date expireDate = extractClaims(token).getExpiration();
        return expireDate.before(new Date());
    }

    private static Claims extractClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
