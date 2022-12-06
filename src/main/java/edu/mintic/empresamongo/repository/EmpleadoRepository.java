package edu.mintic.empresamongo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.mintic.empresamongo.entities.Empleado;

public interface EmpleadoRepository extends MongoRepository<Empleado, String> {
    Optional<Empleado> findByNombre(String nombre);
}
