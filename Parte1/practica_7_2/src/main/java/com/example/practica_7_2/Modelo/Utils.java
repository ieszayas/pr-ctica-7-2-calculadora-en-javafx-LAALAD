package com.example.practica_7_2.Modelo;

public class Utils {

    public static String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%.5f", result).replaceAll("\\.?0*$", "");
        }
    }
}