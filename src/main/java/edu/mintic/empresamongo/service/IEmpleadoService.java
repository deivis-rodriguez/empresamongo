package edu.mintic.empresamongo.service;

import java.util.List;

import edu.mintic.empresamongo.entities.Empleado;

public interface IEmpleadoService {

    public Empleado getEmpleado(String idEmpleado);
    public List<Empleado> listarEmpleados();
    public Empleado crearEmpleado(Empleado empleado);
    public Empleado actualizarEmpleado(Empleado empleado, String idEmpleado);
    public void eliminarEmpleado(String idEmpleado);
    public Empleado buscarPorNombre(String nombreEmpleado);
    
}
