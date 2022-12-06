package edu.mintic.empresamongo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.mintic.empresamongo.entities.ERole;
import edu.mintic.empresamongo.entities.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
    Optional<Role> findByNombre(ERole nombre);
    
}
