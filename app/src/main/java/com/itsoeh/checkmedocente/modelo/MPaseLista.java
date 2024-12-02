package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MPaseLista implements Serializable {

    private int idPase;
    private String fecha;
    private int idInscripcion;
    private int estado;
    private String observaciones;

    public MPaseLista() {
    }

    public MPaseLista(int idPase, String fecha, int idInscripcion, int estado, String observaciones) {
        this.idPase = idPase;
        this.fecha = fecha;
        this.idInscripcion = idInscripcion;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdPase() {
        return idPase;
    }

    public void setIdPase(int idPase) {
        this.idPase = idPase;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "MPaseLista{" +
                "idPase=" + idPase +
                ", fecha='" + fecha + '\'' +
                ", idInscripcion=" + idInscripcion +
                ", estado=" + estado +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
