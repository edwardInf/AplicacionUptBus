package com.example.gcoaquira.aplicacionuptbus.modelo;

public class Buses {
    private int id;
    private String nombre;

    public Buses() {
    }

    public Buses(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
