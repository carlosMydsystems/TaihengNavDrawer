package com.example.sistemas.taihengnavdrawer.Entidades;

import java.io.Serializable;

public class HojaRuta implements Serializable {


    private String NumeroHojaRuta;
    private String NumeroPlaca;
    private String Placa;
    private String Marca;
    private String Chofer;
    private String Id_Clliente;
    private String Nombre;
    private String fechaRegistro;
    private String distrito;
    private String lugarEntrega;

    public HojaRuta() {
    }

    public HojaRuta(String numeroHojaRuta, String numeroPlaca, String placa, String marca,
                    String chofer, String id_Clliente, String nombre, String fechaRegistro,
                    String distrito, String lugarEntrega) {
        NumeroHojaRuta = numeroHojaRuta;
        NumeroPlaca = numeroPlaca;
        Placa = placa;
        Marca = marca;
        Chofer = chofer;
        Id_Clliente = id_Clliente;
        Nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.distrito = distrito;
        this.lugarEntrega = lugarEntrega;
    }

    public String getNumeroHojaRuta() {
        return NumeroHojaRuta;
    }

    public void setNumeroHojaRuta(String numeroHojaRuta) {
        NumeroHojaRuta = numeroHojaRuta;
    }

    public String getNumeroPlaca() {
        return NumeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        NumeroPlaca = numeroPlaca;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getChofer() {
        return Chofer;
    }

    public void setChofer(String chofer) {
        Chofer = chofer;
    }

    public String getId_Clliente() {
        return Id_Clliente;
    }

    public void setId_Clliente(String id_Clliente) {
        Id_Clliente = id_Clliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }
}
