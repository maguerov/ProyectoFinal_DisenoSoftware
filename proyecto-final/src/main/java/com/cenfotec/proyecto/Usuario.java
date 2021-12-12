package com.cenfotec.proyecto;

import java.util.Date;

public class Usuario {

    private String nombreCompleto;
    private String idenfificacion;
    private String correo;
    private String telefono;
    private String carroSeleccionado;
    private double monto;
    private String estadoSubasta;
    private Date fechaSolicitud;

    public Usuario(String nombreCompleto, String idenfificacion, String correo, String telefono, String carroSeleccionado, double monto, String estadoSubasta, Date fechaSolicitud) {
        this.nombreCompleto = nombreCompleto;
        this.idenfificacion = idenfificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.carroSeleccionado = carroSeleccionado;
        this.monto = monto;
        this.estadoSubasta = estadoSubasta;
        this.fechaSolicitud = fechaSolicitud;
    }

    public Usuario(String nombreCompleto, String telefono, String idenfificacion, String correo, String carroSeleccionado, double monto, Date fechaSolicitud) {
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.idenfificacion = idenfificacion;
        this.correo = correo;
        this.carroSeleccionado = carroSeleccionado;
        this.monto = monto;
        this.estadoSubasta = "Abierto";
        this.fechaSolicitud = fechaSolicitud;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdenfificacion() {
        return idenfificacion;
    }

    public void setIdenfificacion(String idenfificacion) {
        this.idenfificacion = idenfificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarroSeleccionado() {
        return carroSeleccionado;
    }

    public void setCarroSeleccionado(String carroSeleccionado) {
        this.carroSeleccionado = carroSeleccionado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }


    public String getEstadoSubasta() {
        return estadoSubasta;
    }

    public void cambiarEstadoSubasta() {
        if (this.estadoSubasta.equals("Abierto")) {
            this.estadoSubasta = "Finalizado";
        }
    }


    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
