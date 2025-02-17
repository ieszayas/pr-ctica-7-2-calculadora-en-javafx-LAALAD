public class Operaciones {
private int operando_1;
private int operando_2;
private String operador;

    public boolean formato_correcto(String recibido){
        String [] operacion = recibido.split(" ");
        try{
            operando_1 = Integer.valueOf(operacion[0]);
            operador = operacion[1];
            operando_2 = Integer.valueOf(operacion[2]);
            if(!(operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/"))){
                return false
            }
        } catch (ClassCastException e) {
            return false;
        }
    }

    public String calcular(String recibido){
        if(formato_correcto(recibido)){
            switch (operador){
                case "+":
                    return operando_1 + operando_2;
                break;
                case "-":
                    return operando_1 - operando_2;
                break;
                case "*":
                    return operando_1 * operando_2;
                break;
                case "/":
                    if (operando_2 == 0){
                        return "NaN"
                    }
                    return operando_1 / operando_2;
                break;
            }
        }
        else{
            return "Err";
        }

    }
}