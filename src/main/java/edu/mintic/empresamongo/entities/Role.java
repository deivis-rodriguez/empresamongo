package edu.mintic.empresamongo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {
    @Id
    private String id;
    private ERole nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getNombre() {
        return nombre;
    }

    public void setNombre(ERole nombre) {
        this.nombre = nombre;
    }

    public Role() {
    }

    public Role(ERole nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", nombre=" + nombre + "]";
    }

    

}
