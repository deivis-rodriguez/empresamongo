package edu.mintic.empresamongo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.mintic.empresamongo.config.auth.jwt.utils.JwtUtils;
import edu.mintic.empresamongo.controller.payloads.requests.SigninRequest;
import edu.mintic.empresamongo.controller.payloads.requests.SignupRequest;
import edu.mintic.empresamongo.controller.payloads.responses.JwtResponse;
import edu.mintic.empresamongo.controller.payloads.responses.MessageResponse;
import edu.mintic.empresamongo.entities.ERole;
import edu.mintic.empresamongo.entities.Role;
import edu.mintic.empresamongo.entities.Usuario;
import edu.mintic.empresamongo.repository.RoleRepository;
import edu.mintic.empresamongo.repository.UsuarioRepository;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UsuarioRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("api/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = jwtUtils.generarToken(authenticate);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("api/auth/signup")
    public ResponseEntity<?> registrarUsuario(@RequestBody SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("el nombre de usuario ya existe"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("el correo de usuario ya existe"));
        }

        List<Role> roles = new ArrayList<>();

        for (String role : request.getRoles()){
           Role role2 = roleRepository.findByNombre(ERole.valueOf(role)).orElseThrow();
           roles.add(role2);
        }
        
        Usuario usuario = new Usuario(request.getUsername(), request.getEmail(), request.getPassword(), roles);
        return ResponseEntity.ok(userRepository.save(usuario));

    }

}
