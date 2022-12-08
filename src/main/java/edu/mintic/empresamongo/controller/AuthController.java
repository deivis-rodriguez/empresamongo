package edu.mintic.empresamongo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.mintic.empresamongo.config.auth.jwt.utils.JwtUtils;
import edu.mintic.empresamongo.config.auth.model.UserDetailsImpl;
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
@CrossOrigin(origins = { "http://localhost:4200" })
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UsuarioRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsService detailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("api/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request) {
        UserDetailsImpl detailsImpl = (UserDetailsImpl) detailsService.loadUserByUsername(request.getUsername());
        
        if(!detailsImpl.getPassword().equals(request.getPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("la contraseña no es correcta"));
        }
        
        String jwt = jwtUtils.generarToken(detailsImpl);

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

        for (String role : request.getRoles()) {
            Role role2 = roleRepository.findByNombre(ERole.valueOf(role)).orElseThrow();
            roles.add(role2);
        }

        Usuario usuario = new Usuario(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()),
                roles);
        return ResponseEntity.ok(userRepository.save(usuario));

    }

}
