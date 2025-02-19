package com.example.practica_7_2.Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/javafx.vista/Ventana_calculadora.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        // Cargar el archivo CSS
        scene.getStylesheets().add(getClass().getResource("/javafx.vista/VentanaCalculadoraStyle.css").toExternalForm());
        // Agregar el icono de la aplicación
        Image icono = new Image(getClass().getResourceAsStream("/javafx.vista/icon.png"));
        stage.getIcons().add(icono); //.webp no lo coge bien.


        stage.setScene(scene);
        stage.setTitle("Calculadora");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}