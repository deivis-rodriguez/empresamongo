package edu.mintic.empresamongo.config.auth.jwt.utils;

//import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.mintic.empresamongo.config.auth.model.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    private final Long tiempo = 30 * 60 * 1000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${empresa.app.jwtSecret}")
    private String secrect; // = Base64.getEncoder().encodeToString("SECRET".getBytes());

    public String generarToken(UserDetailsImpl userPrincipal) {
       // UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tiempo))
                .signWith(SignatureAlgorithm.HS512, secrect.getBytes())
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(secrect.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOGGER.error("se generó un error leyendo el token: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameDelToken(String token) {
        return Jwts.parser().setSigningKey(secrect.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

}
