package com.example.sistemas.taihengnavdrawer.Entidades;

import java.io.Serializable;

public class DetalleHojaRuta implements Serializable {

    private String numerohojaruta;
    private String codCliente;
    private String cliente;
    private String lugarEntrega;
    private String numerodocumento;
    private String fechadocumento;
    private String moneda;
    private String importedocumento;
    private String estado;
    private String distrito;
    private String codDocumento;

    public DetalleHojaRuta() {
    }

    public DetalleHojaRuta(String numerohojaruta, String codCliente, String cliente,
                           String lugarEntrega, String numerodocumento, String fechadocumento,
                           String moneda, String importedocumento, String estado, String distrito,
                           String codDocumento) {
        this.numerohojaruta = numerohojaruta;
        this.codCliente = codCliente;
        this.cliente = cliente;
        this.lugarEntrega = lugarEntrega;
        this.numerodocumento = numerodocumento;
        this.fechadocumento = fechadocumento;
        this.moneda = moneda;
        this.importedocumento = importedocumento;
        this.estado = estado;
        this.distrito = distrito;
        this.codDocumento = codDocumento;
    }

    public String getNumerohojaruta() {
        return numerohojaruta;
    }

    public void setNumerohojaruta(String numerohojaruta) {
        this.numerohojaruta = numerohojaruta;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getFechadocumento() {
        return fechadocumento;
    }

    public void setFechadocumento(String fechadocumento) {
        this.fechadocumento = fechadocumento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImportedocumento() {
        return importedocumento;
    }

    public void setImportedocumento(String importedocumento) {
        this.importedocumento = importedocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }
}
