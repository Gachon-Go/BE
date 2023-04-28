package gcu.mp.security.SecurityConfig.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;


@Slf4j
@Component
public class JwtTokenProvider {

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String TOKEN_TYPE = "token_type";
    private static final String ACCESS_TOKEN = "access_token";

    private final SecretKey secretKey;

    private final long accessTokenValidityInMillis;


    private final UserDetailsService userDetailsService;

    private final JwtParser jwtParser;

    public JwtTokenProvider(
            @Value("100000000") long accessTokenValidity,
            //랜덤 문자열 생성
            // https://www.random.org/strings/
            @Value("3bWNO73XHugZHzVjVCy03cAqD91NQU6BJmTHAp1nrKXBpKfs0Soz00bkTnYE1DIwRRAGg592CdGZ5kj94xmZXw2nl") String secret,
            UserDetailsService userDetailsService) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenValidityInMillis = accessTokenValidity * 1000 * 60 * 60;
        this.userDetailsService = userDetailsService;
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public String createAccessToken(String username) {
        return createToken(username, ACCESS_TOKEN);
    }

    private String createToken(String username, String tokenType) {
        return builder()
                .setSubject(username)
                .claim(TOKEN_TYPE, tokenType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 유효성 및 만료기간 검사
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: ", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: ", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: ", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: ", e);
        }
        return false;
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String accessToken) {
        String usernameFromToken = jwtParser.parseClaimsJws(accessToken).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getSubject(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

}