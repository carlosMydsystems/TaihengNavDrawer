package com.example.sistemas.taihengnavdrawer.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String Id_Usuario;
    private String Nickname;
    private String Nombre;
    private String Apellido;
    private String Perfil;

    public Usuario(String id_Usuario, String nickname, String nombre, String apellido, String perfil) {
        Id_Usuario = id_Usuario;
        Nickname = nickname;
        Nombre = nombre;
        Apellido = apellido;
        Perfil = perfil;
    }

    public Usuario() {
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(String id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }
}
