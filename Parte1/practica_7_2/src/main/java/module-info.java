module com.example.practica_7_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.practica_7_2.Controlador to javafx.fxml;
    exports com.example.practica_7_2.Controlador;
}