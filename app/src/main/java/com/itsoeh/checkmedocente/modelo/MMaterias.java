package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MMaterias implements Serializable {

    private String clave;
    private String nombre;
    private String app;
    private String apm;
    private String nombreAsig;
    private int idInscripcion;


    public MMaterias() {
    }

    public MMaterias(String clave, String nombre, String app, String apm, String nombreAsig, int idInscripcion) {
        this.clave = clave;
        this.nombre = nombre;
        this.app = app;
        this.apm = apm;
        this.nombreAsig = nombreAsig;
        this.idInscripcion = idInscripcion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getNombreAsig() {
        return nombreAsig;
    }

    public void setNombreAsig(String nombreAsig) {
        this.nombreAsig = nombreAsig;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    @Override
    public String toString() {
        return "MMaterias{" +
                "clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", app='" + app + '\'' +
                ", apm='" + apm + '\'' +
                ", nombreAsig='" + nombreAsig + '\'' +
                ", idInscripcion=" + idInscripcion +
                '}';
    }
}
