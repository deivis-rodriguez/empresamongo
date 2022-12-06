package edu.mintic.empresamongo.entities;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "empleados")
public class Empleado {

    private @Id String idEmpleado;
    private String nombre;
    private String apellido;
    private double salario;
    private String cargo;
    private List<Integer> pagos;
    private List<Proyecto> proyectos;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, double salario, String cargo, List<Integer> pagos, List<Proyecto> proyectos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.salario = salario;
        this.cargo = cargo;
        this.pagos = pagos;
        this.proyectos = proyectos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return this.apellido;
    }

    public String getCargo() {
        return this.cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String toString() {
        return "idEmpleado: " + idEmpleado + "\n"
                + "nombre: " + nombre + "\n"
                + "apellido: " + apellido + "\n"
                + "cargo: " + cargo + "\n"
                + "salario: " + salario + "\n";
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<Integer> getPagos() {
        return pagos;
    }

    public void setPagos(List<Integer> pagos) {
        this.pagos = pagos;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }


}
