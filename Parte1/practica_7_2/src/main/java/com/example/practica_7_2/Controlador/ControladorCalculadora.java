package com.example.practica_7_2.Controlador;

import com.example.practica_7_2.Modelo.CalculadoraModelo;
import com.example.practica_7_2.Modelo.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ControladorCalculadora {
    private String memoria = "";
    private boolean usandoMemoria = false;
    @FXML
    private Label pantalla;
    @FXML
    private Label pantalla_small; // Label para mostrar el historial

    private String operador = "";
    private double primerNumero = 0;
    private boolean esOperacionRealizada = false;
    private boolean esperandoSegundoOperando = false;
    // Guarda la parte ya ingresada de la operación (por ejemplo, "4+")
    private String historialOperacion = "";

    private CalculadoraModelo modelo = new CalculadoraModelo();

    @FXML
    public void initialize() {
        pantalla.setText("");
        pantalla_small.setText("");
    }

    @FXML
    protected void onClickIgual() {
        String current = pantalla.getText().trim();
        if (current.isEmpty()) {
            mostrarAdvertencia("No hay número para calcular");
            return;
        }
        // Si no se ha seleccionado ningún operador, se muestra el mismo número en el historial con '='
        if (operador.isEmpty()) {
            pantalla_small.setText(current + "=");
            // Se mantiene el número en el display
            return;
        }
        // En caso de que se haya seleccionado un operador, se actualiza el historial y se realiza la operación
        pantalla_small.setText(historialOperacion + current + "=");
        realizarOperacion();
    }


    /**
     * Maneja el uso del botón de paréntesis para ingresar números negativos en el segundo operando.
     * Solo se permite cuando se está esperando el segundo operando y el display está vacío.
     */
    @FXML
    public void onClickParentesis() {
        String current = pantalla.getText();

        // Si ya se inició el número negativo y no está cerrado, permitimos cerrar
        if (current.startsWith("(-") && !current.endsWith(")")) {
            if (current.length() > 2) {  // Debe haber al menos un dígito después de "(-"
                pantalla.setText(current + ")");
                pantalla_small.setText(historialOperacion + pantalla.getText());
            } else {
                mostrarAdvertencia("Ingrese el número negativo antes de cerrar el paréntesis.");
            }
            return;
        }

        // Si se está en modo de espera para el segundo operando, permitimos abrir el paréntesis
        if (esperandoSegundoOperando) {
            if (current.isEmpty() || current.equals("0")) {
                pantalla.setText("(-");
                pantalla_small.setText(historialOperacion + "(-");
            } else {
                mostrarAdvertencia("El número ya está iniciado. Para números negativos, inícielo con '(-'.");
            }
        } else {
            // En cualquier otro caso (por ejemplo, en el primer operando) mostramos el mensaje de error
            mostrarAdvertencia("Para número negativo en el primer operando, use el signo '-' al inicio.");
        }
    }





    @FXML
    public void onClickPunto(ActionEvent event) {
        limpiarSiError();
        String textoActual = pantalla.getText();
        if (textoActual.isEmpty()) {
            pantalla.setText("0.");
            return;
        }
        if (!textoActual.contains(".")) {
            pantalla.setText(textoActual + ".");
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                pantalla_small.setText(historialOperacion + pantalla.getText());
            }
        }
    }

    /**
     * Realiza la operación, considerando la posibilidad de que el segundo operando
     * se haya ingresado entre paréntesis (por ejemplo, "(-3)").
     */
    private void realizarOperacion() {
        String text = pantalla.getText();

        // Verificar y completar el manejo de paréntesis para números negativos (si corresponde)
        if (text.startsWith("(-") && !text.endsWith(")")) {
            mostrarAdvertencia("Cierre el paréntesis para el operando negativo.");
            return;
        }
        if (text.startsWith("(-") && text.endsWith(")")) {
            text = "-" + text.substring(2, text.length() - 1);
        }
        text = text.replace(',', '.');

        double segundoNumero = modelo.convertirNumero(text);
        if (Double.isNaN(segundoNumero)) {
            pantalla.setText("Error");
            return;
        }

        double resultado = modelo.calcular(primerNumero, segundoNumero, operador);
        if (!Double.isNaN(resultado)) {
            String resultStr = Utils.formatResult(resultado);
            pantalla.setText(resultStr);
            // Solo se actualiza la memoria si se usó el botón de memoria en la operación.
            if (usandoMemoria) {
                memoria = resultStr;
                usandoMemoria = false;  // Reiniciamos la bandera para futuras operaciones.
            }
        } else {
            pantalla.setText("Error");
        }

        operador = "";
        esOperacionRealizada = true;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }


    /**
     * Maneja la pulsación de un operador.
     * Para el primer operando, si el display está vacío y se pulsa '-' se tratará como signo negativo.
     */
    public void onClickOperacion(ActionEvent event) {
        limpiarSiError();
        String oper = ((Button) event.getSource()).getText();

        // Si el display está vacío y se pulsa "-" para el primer operando, se trata como signo negativo
        if (pantalla.getText().isEmpty() && oper.equals("-")) {
            pantalla.setText("-");
            return;
        }

        // Si ya se está esperando el segundo operando, se interpreta que se desea cambiar el operador
        if (esperandoSegundoOperando) {
            operador = oper;
            if (!historialOperacion.isEmpty()) {
                // Reemplaza el último carácter (el operador anterior) por el nuevo.
                historialOperacion = historialOperacion.substring(0, historialOperacion.length() - 1) + operador;
            } else {
                historialOperacion = pantalla.getText() + operador;
            }
            pantalla_small.setText(historialOperacion);
            return;
        }

        if (esOperacionRealizada) {
            esOperacionRealizada = false;
        }

        String currentText = pantalla.getText().trim();
        if (currentText.equals("Error") || currentText.isEmpty() || !currentText.matches("[+-]?\\d*([.,]\\d+)?")) {
            currentText = "0";
        } else {
            currentText = currentText.replace(',', '.');
        }

        primerNumero = modelo.convertirNumero(currentText);
        if (Double.isNaN(primerNumero)) {
            mostrarAdvertencia("Valor inválido para la operación");
            return;
        }

        operador = oper;
        // Actualizamos el historial con el operando actual y el operador
        historialOperacion = currentText + operador;
        pantalla_small.setText(historialOperacion);
        // Limpiamos el display para que el segundo operando empiece vacío
        pantalla.setText("");
        esperandoSegundoOperando = true;

    }


    public void onClickNumero(ActionEvent event) {
        limpiarSiError();
        String numero = ((Button) event.getSource()).getText();
        if (esperandoSegundoOperando) {
            // Si el display ya contiene "(-", no lo reemplazamos sino que concatenamos el dígito.
            if (pantalla.getText().startsWith("(-")) {
                pantalla.setText(pantalla.getText() + numero);
            } else {
                pantalla.setText(numero);
            }
            esperandoSegundoOperando = false;
            pantalla_small.setText(historialOperacion + pantalla.getText());
        } else {
            if (esOperacionRealizada) {
                pantalla.setText(numero);
                pantalla_small.setText("");
                esOperacionRealizada = false;
            } else {
                if (pantalla.getText().equals("0") || pantalla.getText().isEmpty()) {
                    pantalla.setText(numero);
                } else {
                    pantalla.setText(pantalla.getText() + numero);
                }
                if (!historialOperacion.isEmpty()) {
                    pantalla_small.setText(historialOperacion + pantalla.getText());
                }
            }
        }
    }


    public void onClickBorrar() {
        String texto = pantalla.getText();
        if (!texto.isEmpty()) {
            String nuevoTexto = texto.substring(0, texto.length() - 1);
            pantalla.setText(nuevoTexto);
            if (!historialOperacion.isEmpty() && !esperandoSegundoOperando) {
                pantalla_small.setText(historialOperacion + nuevoTexto);
            }
        }
    }

    public void onClickResetear() {
        pantalla.setText("");
        pantalla_small.setText("");
        operador = "";
        primerNumero = 0;
        esOperacionRealizada = false;
        esperandoSegundoOperando = false;
        historialOperacion = "";
    }

    private void limpiarSiError() {
        if (pantalla.getText().equals("Error")) {
            pantalla.setText("");
        }
    }
    @FXML
    public void onClickCalavera(ActionEvent event) {
        String current = pantalla.getText().trim();

        // Si el display está vacío, no hacemos nada
        if(current.isEmpty()) {
            return;
        }

        String parsedNumber = current;
        // Si el número está en formato con paréntesis, por ejemplo "(-3)", lo convertimos a "-3"
        if(current.startsWith("(-") && current.endsWith(")")) {
            parsedNumber = "-" + current.substring(2, current.length() - 1);
        }

        try {
            // Convertimos el número (reemplazamos la coma por punto si fuera necesario)
            double value = Double.parseDouble(parsedNumber.replace(',', '.'));
            double toggled = -value;
            // Formateamos el resultado utilizando tu método de utilidades
            String formatted = Utils.formatResult(toggled);
            pantalla.setText(formatted);

            // Opcional: si estás mostrando el historial y deseas actualizarlo, puedes hacerlo aquí.
            // Por ejemplo, si el historial contiene el número actual, reemplázalo.
            // En este ejemplo se deja sin modificar el label_historial.

        } catch(NumberFormatException e) {
            mostrarAdvertencia("No se puede cambiar el signo");
        }
    }

    /**
     * Muestra un "toast" no modal que se autodesvanece después de 2 segundos.
     */
    private void mostrarAdvertencia(String mensaje) {
        Platform.runLater(() -> {
            Popup popup = new Popup();
            Label toastLabel = new Label(mensaje);
            toastLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"
                    + " -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5;");
            popup.getContent().add(toastLabel);
            Stage stage = (Stage) pantalla.getScene().getWindow();
            popup.show(stage);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> popup.hide());
            delay.play();
        });
    }
    @FXML
    public void onClickM_plus(ActionEvent event) {
        String currentText = pantalla.getText().trim();

        if (currentText.isEmpty()) {
            mostrarAdvertencia("No hay nada para guardar en la memoria.");
            return;
        }

        try {
            // Convertir el número actual (reemplazando coma por punto si es necesario)
            double currentNumber = modelo.convertirNumero(currentText.replace(',', '.'));
            double memoryNumber;

            if (memoria.isEmpty()) {
                // Primera vez: se guarda el número actual en memoria.
                memoryNumber = currentNumber;
            } else {
                // Usos posteriores: se suma el número actual con el valor guardado en memoria.
                double storedNumber = modelo.convertirNumero(memoria.replace(',', '.'));
                memoryNumber = storedNumber + currentNumber;
            }

            // Formatear el nuevo valor de memoria.
            String newMemory = Utils.formatResult(memoryNumber);
            memoria = newMemory;
            usandoMemoria = true;  // Se marca que se está usando la memoria en esta operación.

            // Se actualiza el display y el historial para reflejar la acción.
            pantalla.setText(newMemory);
            pantalla_small.setText("MR = " + newMemory);
            mostrarAdvertencia("Memoria actualizada: " + newMemory);
        } catch (Exception e) {
            mostrarAdvertencia("Error al procesar la memoria.");
        }
    }
    @FXML
    public void onClickMC(ActionEvent event) {
        memoria = "0";
        pantalla_small.setText("MR = 0");
        mostrarAdvertencia("Memoria reiniciada a 0");
    }

    @FXML
    public void onClickMR(ActionEvent event) {
        if (memoria.isEmpty()) {
            mostrarAdvertencia("No hay memoria almacenada.");
            return;
        }

        // Si se está esperando el segundo operando (por ejemplo, después de pulsar un operador)
        if (esperandoSegundoOperando) {
            // Se inserta el valor de la memoria como el segundo operando
            pantalla.setText(memoria);
            pantalla_small.setText(historialOperacion + memoria);
            esperandoSegundoOperando = false; // Se marca que ya se ha ingresado el segundo operando
        } else {
            // Si no se está en medio de una operación, simplemente se muestra la memoria
            pantalla.setText(memoria);
            pantalla_small.setText("MR = " + memoria);
        }
    }

}