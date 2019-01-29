package com.example.gcoaquira.aplicacionuptbus.modelo;

public class Conductores {
    private int id;
    private String nombres;
    private String apellidos;
    private String codigo;
    private String clave;

    public Conductores() {
    }

    public Conductores(int id, String nombres, String apellidos, String codigo, String clave) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.codigo = codigo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
