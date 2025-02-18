// Modelo: Realiza los cálculos de la calculadora
public class CalculadoraModelo {


    private int num1;
    private int num2;
    private String operador;
    private boolean nuevoNumero = true;

    public void setOperando(double numero){
        if(){
            pantalla.setText(numero);
            nuevoNumero = false;
        }else{
            vActual = (vActual*10) + numero;
        }
    }

    public void setOperador(String operador){

        nuevoNumero = true;
    }

    // Método para realizar los cálculos según el operador
    public double calcular(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return sumar(num1, num2);
            case "-":
                return restar(num1, num2);
            case "*":
                return multiplicar(num1, num2);
            case "/":
                return dividir(num1, num2);
            default:
                return Double.NaN;
        }
    }

    // Método para sumar dos números
    private double sumar(double num1, double num2) {
        return num1 + num2;
    }

    // Método para restar dos números
    private double restar(double num1, double num2) {
        return num1 - num2;
    }

    // Método para multiplicar dos números
    private double multiplicar(double num1, double num2) {
        return num1 * num2;
    }

    // Método para dividir dos números con control de división por cero
    private double dividir(double num1, double num2) {
        if (num2 == 0) {
            return Double.NaN; // Retorna NaN en caso de división por cero
        } else {
            return num1 / num2; // Realiza la división normalmente
        }
    }
}