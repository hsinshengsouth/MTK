package idv.po.mtk_src.infrastructure.AuthUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${application.security.jwt.secret-key}")
    private   String SECRET_KEY ;

    @Value("${application.security.jwt.expiration-time}")
    private  long jwtExpirationMs ;


    public String getSecretKey() {
        return SECRET_KEY;
    }


    public Long getExpirationMs() {
        return jwtExpirationMs;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +  getExpirationMs()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUserName(String token){
        return extraClaim(token,Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
       return  extraClaim(token,Claims::getExpiration).before(new Date());
    }




    private <T> T extraClaim(String token, Function<Claims,T> claimsResolver){
        final  Claims claims= Jwts.parser().setSigningKey( getSignInKey()).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
