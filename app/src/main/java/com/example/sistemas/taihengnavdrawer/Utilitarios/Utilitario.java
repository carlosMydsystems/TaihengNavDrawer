package com.example.sistemas.taihengnavdrawer.Utilitarios;

public class Utilitario {

    public static String Soles = "S/"; // Cambio de moneda en Soles
    public static String Dolares = "USD";  // Cambio de moneda en Dólares
    public static String Version = "Versión 1.0.1";  // Cambio de moneda en Dólares
    public static String webServiceCursormovil =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorDesaMovil.php?funcion=";
    public static String webServicemovil =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionDesaMovil.php?funcion=";

    public static final Integer PHONESTATS = 0x1;

    public static String formatoFecha(Integer dateTime){

        String valor = "0";
        if (dateTime <=9){
            valor = valor + dateTime;
        }else {
            valor = dateTime + "";
        }
        return valor;
    }

}
