package com.itsoeh.checkmedocente.modelo;

public class MEstudiante {
    private String matricula;
    private String nombre, idGrupo, correo, idInscripcion;
    private  int idEstudiante;

    public MEstudiante() {
    }

    public MEstudiante(String matricula, String nombre, String idGrupo, String correo, String idInscripcion, int idEstudiante) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.idGrupo = idGrupo;
        this.correo = correo;
        this.idInscripcion = idInscripcion;
        this.idEstudiante = idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public String toString() {
        return "MEstudiante{" +
                "matricula='" + matricula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idGrupo='" + idGrupo + '\'' +
                ", correo='" + correo + '\'' +
                ", idInscripcion='" + idInscripcion + '\'' +
                ", idEstudiante=" + idEstudiante +
                '}';
    }
}

