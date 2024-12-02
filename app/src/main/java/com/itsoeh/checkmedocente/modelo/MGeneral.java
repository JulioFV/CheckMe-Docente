package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MGeneral  implements Serializable {
    private int idHistorial;
    private int creditos;
    private int prom;
    private int idEstudiante;
    private int idPeriodo;
    private String periodo;

    public MGeneral() {
    }

    public MGeneral(int idHistorial, int creditos, int prom, int idEstudiante, int idPeriodo, String periodo) {
        this.idHistorial = idHistorial;
        this.creditos = creditos;
        this.prom = prom;
        this.idEstudiante = idEstudiante;
        this.idPeriodo = idPeriodo;
        this.periodo = periodo;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getProm() {
        return prom;
    }

    public void setProm(int prom) {
        this.prom = prom;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "MHistorial{" +
                "idHistorial=" + idHistorial +
                ", creditos=" + creditos +
                ", prom=" + prom +
                ", idEstudiante=" + idEstudiante +
                ", idPeriodo=" + idPeriodo +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
