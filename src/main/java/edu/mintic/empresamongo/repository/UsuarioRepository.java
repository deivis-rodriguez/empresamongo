package edu.mintic.empresamongo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.mintic.empresamongo.entities.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String Username);
}
