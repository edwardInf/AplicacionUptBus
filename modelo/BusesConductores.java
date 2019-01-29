package com.example.gcoaquira.aplicacionuptbus.modelo;

public class BusesConductores {
    private int id;
    private Conductores conductor;
    private Buses bus;
    private float lat;
    private float lng;

    public BusesConductores() {
    }

    public BusesConductores(int id, Conductores conductor, Buses bus, float lat, float lng) {
        this.id = id;
        this.conductor = conductor;
        this.bus = bus;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conductores getConductor() {
        return conductor;
    }

    public void setConductor(Conductores conductor) {
        this.conductor = conductor;
    }

    public Buses getBus() {
        return bus;
    }

    public void setBus(Buses bus) {
        this.bus = bus;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
