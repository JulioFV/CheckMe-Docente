package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MHistorial implements Serializable {
    private int idHistorial;
    private int idEstudiante;
    private double promedio;
    private int creditos;
    private int idPeriodo;

    public MHistorial() {
    }

    public MHistorial(int idHistorial, int idEstudiante, double promedio, int creditos, int idPeriodo) {
        this.idHistorial = idHistorial;
        this.idEstudiante = idEstudiante;
        this.promedio = promedio;
        this.creditos = creditos;
        this.idPeriodo = idPeriodo;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public String toString() {
        return "MHistorial{" +
                "idHistorial=" + idHistorial +
                ", idEstudiante=" + idEstudiante +
                ", promedio=" + promedio +
                ", creditos=" + creditos +
                ", idPeriodo=" + idPeriodo +
                '}';
    }
}
