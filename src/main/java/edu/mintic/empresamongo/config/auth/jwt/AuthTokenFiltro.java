package edu.mintic.empresamongo.config.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import org.springframework.web.filter.OncePerRequestFilter;

import edu.mintic.empresamongo.config.auth.jwt.utils.JwtUtils;
import edu.mintic.empresamongo.config.auth.services.UserDetailsServiceImpl;

public class AuthTokenFiltro extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl detailsServiceImpl;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFiltro.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                token = token.substring(7, token.length());
            } else {
                token = null;
            }

            if (token != null && jwtUtils.validarToken(token)) {
                String username = jwtUtils.getUsernameDelToken(token);
                UserDetails usuario = detailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(usuario,
                        null, usuario.getAuthorities());
                autenticacion.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }

        } catch (Exception e) {
            LOGGER.error("no se pudo autenticar: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
