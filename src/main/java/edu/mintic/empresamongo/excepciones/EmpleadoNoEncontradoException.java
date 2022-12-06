package edu.mintic.empresamongo.excepciones;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException(String idEmpleado) {
        super("No existe un empleado en la BD con id: " + idEmpleado);
    }

}
