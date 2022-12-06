package edu.mintic.empresamongo.config.auth.jwt.utils;

import java.util.Date;

import org.springframework.security.core.Authentication;

import edu.mintic.empresamongo.config.auth.model.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

    private final Long tiempo = 30 * 60 * 1000L;
    private final String secrect = "SECRET";

    public String generarToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date( new Date().getTime() + tiempo))
                .signWith(SignatureAlgorithm.RS256, secrect)
                .compact();
    }

}
