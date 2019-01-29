package com.example.gcoaquira.aplicacionuptbus.modelo;

public class Estudiantes {
    private String codigo;
    private String clave;
    private String nombres;
    private String apellidos;
    private String email;


    public Estudiantes(String codigo, String clave, String nombres, String apellidos, String email) {
        this.codigo = codigo;
        this.clave = clave;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getClave() {
        return clave;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }
}
