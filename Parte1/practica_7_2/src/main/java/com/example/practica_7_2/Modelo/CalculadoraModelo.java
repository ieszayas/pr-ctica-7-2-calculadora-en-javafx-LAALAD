package com.example.practica_7_2.Modelo;

public class CalculadoraModelo {

    public double calcular(double num1, double num2, String operador) {
        switch (operador) {
            case "➕": return num1 + num2;
            case "➖": return num1 - num2;
            case "✖": return num1 * num2;
            case "➗":
                if (num2 == 0) return Double.NaN; // Evitar división por cero
                return num1 / num2;
            case "%": return num1 * num2 / 100;
            default: return Double.NaN;
        }
    }

    public double convertirNumero(String texto) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}