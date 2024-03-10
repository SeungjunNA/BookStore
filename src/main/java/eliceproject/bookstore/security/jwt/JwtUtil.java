package eliceproject.bookstore.security.jwt;

import eliceproject.bookstore.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String createToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        Claims claims = Jwts.claims();
        claims.put("username", username);

        long expireTime = 1000 * 60 * 60;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsername(String token){
        return extractClaims(token).get("username").toString();
    }

    public boolean isExpired(String token){
        Date expireDate = extractClaims(token).getExpiration();
        return expireDate.before(new Date());
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
