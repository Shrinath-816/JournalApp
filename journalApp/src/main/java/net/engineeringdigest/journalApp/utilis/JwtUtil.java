package net.engineeringdigest.journalApp.utilis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {

    private String SECRET_KEY = "Tak+Hav^dshb34747$SBJ<>?!@#^&*SSDYHS45^&htrsd";

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration() ;
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload() ;
    }/*
    */

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return createToken(claims,username);
    }


    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token){
        final String extractedUsername = extractUsername(token);
        return   !isTokenExpired(token);
    }
}
