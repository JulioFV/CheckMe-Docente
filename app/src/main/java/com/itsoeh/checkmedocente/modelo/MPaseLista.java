package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MPaseLista implements Serializable {

    private int idPase;
    private String fecha;
    private int idInscripcion;
    private int estado;
    private String observaciones;
    private int opcion;

    public MPaseLista() {
    }

    public MPaseLista(int idPase, String fecha, int idInscripcion, int estado, String observaciones, int opcion) {
        this.idPase = idPase;
        this.fecha = fecha;
        this.idInscripcion = idInscripcion;
        this.estado = estado;
        this.observaciones = observaciones;
        this.opcion = opcion;
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

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    @Override
    public String toString() {
        return "MPaseLista{" +
                "idPase=" + idPase +
                ", fecha='" + fecha + '\'' +
                ", idInscripcion=" + idInscripcion +
                ", estado=" + estado +
                ", observaciones='" + observaciones + '\'' +
                ", opcion=" + opcion +
                '}';
    }
}
