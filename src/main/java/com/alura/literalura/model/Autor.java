package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cambiado a minúscula para seguir convenciones y evitar confusión con la clase Wrapper Long
    private String nombre;
    private Integer anoNacimiento;
    private Integer anoFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

    public Autor() {
        // Constructor vacío requerido por JPA
    }

    public Autor(DatosAutor d) {
        this.nombre = d.nombre();
        this.anoNacimiento = d.anoNacimiento();
        this.anoFallecimiento = d.anoFallecimiento();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(Integer anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return String.format("""
                ********** Autor **********
                Nombre = %s
                Fecha de Nacimiento = %d
                Fecha de Muerte = %d
                ***************************
                """, nombre, anoNacimiento, anoFallecimiento);
    }
}
