package edu.mintic.empresamongo.config.auth.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.mintic.empresamongo.config.auth.model.UserDetailsImpl;
import edu.mintic.empresamongo.entities.Usuario;
import edu.mintic.empresamongo.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("el usuario no se encuentra registrado!");
                });
        return UserDetailsImpl.build(usuario);
    }

}
