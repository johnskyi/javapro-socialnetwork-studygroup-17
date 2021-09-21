package ru.skillbox.socialnetwork.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${spring.security.jwt.tokenValidTimeInMinutes}")
    private long tokenValidTimeInMinutes;
    @Value("${spring.security.jwt.tokenSecretKey}")
    private String tokenSecretKey;
    @Value("${spring.security.jwt.authHeader")
    private String authHeader;

    public JWTTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init(){
        tokenSecretKey = Base64.getEncoder().encodeToString(tokenSecretKey.getBytes());
    }

    public String createToken(String username, String role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + (tokenValidTimeInMinutes * 60) * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtException("Jwt token is expired or invalid");
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(authHeader);
    }


}
