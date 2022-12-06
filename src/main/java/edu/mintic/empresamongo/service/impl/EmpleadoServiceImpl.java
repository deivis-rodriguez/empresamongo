package edu.mintic.empresamongo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.mintic.empresamongo.entities.Empleado;
import edu.mintic.empresamongo.excepciones.EmpleadoNoEncontradoException;
import edu.mintic.empresamongo.service.IEmpleadoService;
import edu.mintic.empresamongo.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
   
    private final EmpleadoRepository repository;

    EmpleadoServiceImpl(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empleado getEmpleado(String idEmpleado) {
        return repository.findById(idEmpleado).orElseThrow(() -> new EmpleadoNoEncontradoException(idEmpleado));
    }

    @Override
    public List<Empleado> listarEmpleados() {
        return repository.findAll();
    }

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        return repository.save(empleado);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado, String idEmpleado) {
        Empleado empleadoViejo = repository.findById(idEmpleado).orElseThrow();
        empleadoViejo.setApellido(empleado.getApellido());
        empleadoViejo.setNombre(empleado.getNombre());
        empleadoViejo.setCargo(empleado.getCargo());
        empleadoViejo.setSalario(empleado.getSalario());
        empleadoViejo.setProyectos(empleado.getProyectos());
        empleadoViejo.setPagos(empleado.getPagos());
        return repository.save(empleadoViejo);
    }

    @Override
    public void eliminarEmpleado(String idEmpleado) {
        repository.deleteById(idEmpleado);
    }

    @Override
    public Empleado buscarPorNombre(String nombreEmpleado) {
        return repository.findByNombre(nombreEmpleado).orElseThrow();
    }
    
}
