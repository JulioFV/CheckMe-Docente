package com.itsoeh.checkmedocente.modelo;

import java.io.Serializable;

public class MGrupo implements Serializable {
    private int idGrupo;
    private String clave;
    private int idAsignatura;
    private int idDocente;
    private int idPeriodo;
    private String nombreAsig;
    private String nombreDoc;
    private String app;
    private String apm;
    private String nombrePer;

    public MGrupo() {

    }

    public MGrupo(int idGrupo, String clave, int idAsignatura, int idDocente, int idPeriodo, String nombreAsig, String nombreDoc, String app, String apm, String nombrePer) {
        this.idGrupo = idGrupo;
        this.clave = clave;
        this.idAsignatura = idAsignatura;
        this.idDocente = idDocente;
        this.idPeriodo = idPeriodo;
        this.nombreAsig = nombreAsig;
        this.nombreDoc = nombreDoc;
        this.app = app;
        this.apm = apm;
        this.nombrePer = nombrePer;
    }

    public MGrupo(String clave, String nombreAsignatura, String nombreDocente, int periodo,int op) {
        this.clave = clave;
        this.nombreAsig = nombreAsignatura;
        this.nombreDoc = nombreDocente;
        this.idPeriodo = periodo;

    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombreAsig() {
        return nombreAsig;
    }

    public void setNombreAsig(String nombreAsig) {
        this.nombreAsig = nombreAsig;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
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

    public String getNombrePer() {
        return nombrePer;
    }

    public void setNombrePer(String nombrePer) {
        this.nombrePer = nombrePer;
    }

    @Override
    public String toString() {
        return "MGrupo{" +
                "idGrupo=" + idGrupo +
                ", clave='" + clave + '\'' +
                ", idAsignatura=" + idAsignatura +
                ", idDocente=" + idDocente +
                ", idPeriodo=" + idPeriodo +
                ", nombreAsig='" + nombreAsig + '\'' +
                ", nombreDoc='" + nombreDoc + '\'' +
                ", app='" + app + '\'' +
                ", apm='" + apm + '\'' +
                ", nombrePer='" + nombrePer + '\'' +
                '}';
    }
}
