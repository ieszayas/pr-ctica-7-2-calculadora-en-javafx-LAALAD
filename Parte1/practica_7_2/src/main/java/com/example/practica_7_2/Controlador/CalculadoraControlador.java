package com.example.practica_7_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HelloController {
    @FXML

    private Label pantalla;

    private static int cero = 0;
    private static int uno = 1;
    private static int dos=2;
    private static int tres=3;
    private static int cuatro=4;
    private static int cinco=5;
    private static int seis=6;
    private static int siete=7;
    private static int ocho=8;
    private static int nueve=9;
    private static String sumar = "+";
    private static String restar = "-";
    private static String multiplicar = "*";
    private static String dividir = "/";


    //private String[] datos = {String.valueOf(num1),operacion, String.valueOf(num2)}


    @FXML
    protected void onHelloButtonClick() {
        pantalla.setText("Welcome to JavaFX Application!");
    }

    public void onClickResetear(ActionEvent actionEvent) {
        pantalla.setText("");
        num1=0;
        num2=0;
        operacion="";
    }

    public void onClickBorrar(ActionEvent actionEvent) {
    }

    public void onClickSumar(ActionEvent actionEvent) {
        operacion = sumar;

    }

    public void onClickRestar(ActionEvent actionEvent) {
        operacion=restar;
    }

    public void onClickMultiplicar(ActionEvent actionEvent) {
        operacion = multiplicar;
    }

    public void onClickDividir(ActionEvent actionEvent) {
        operacion = dividir;
    }

    public void onClickCero(ActionEvent actionEvent) {
    }

    public void onClickUno(ActionEvent actionEvent) {

    }

    public void onClickDos(ActionEvent actionEvent) {
    }

    public void onClickTres(ActionEvent actionEvent) {
    }

    public void onClickCuatro(ActionEvent actionEvent) {
    }

    public void onClickCinco(ActionEvent actionEvent) {
    }

    public void onClickSeis(ActionEvent actionEvent) {
    }

    public void onClickSiete(ActionEvent actionEvent) {
    }

    public void onClickOcho(ActionEvent actionEvent) {
    }

    public void onClickNueve(ActionEvent actionEvent) {
    }

    public void onClickPunto(ActionEvent actionEvent) {
    }

    public void onClickIgual(ActionEvent actionEvent) {
    }

    public void onClickPorcentaje(ActionEvent actionEvent) {
    }

    public void onClickCalavera(ActionEvent actionEvent) {
    }

}