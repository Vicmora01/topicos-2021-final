package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vBox;
    private TextField txtOperacion;
    private HBox[] hBoxes;
    private Button[] arBotones;
    private Button btnLimpiar;
    private char[] arNumeros = {'7','8','9','/','4','5','6','*','1','2','3','+','0','.','=','-'};

    private double num1, num2;
    private char operacion;
    private int contadorOperandos = 0;
    private int posNum2;

    public Calculadora(){
        crearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(5);

        txtOperacion = new TextField();
        txtOperacion.setEditable(false);
        txtOperacion.setPrefHeight(100);
        txtOperacion.setText("");
        txtOperacion.setFocusTraversable(false);
        txtOperacion.setAlignment(Pos.BASELINE_RIGHT);

        hBoxes = new HBox[4];
        arBotones = new Button[16];
        btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(event -> limpiar());

        int pos = 0;
        for (int i = 0; i < hBoxes.length; i++) {
            hBoxes[i] = new HBox();
            hBoxes[i].setSpacing(5);

            for (int j = 0; j < 4; j++) {
                arBotones[pos] = new Button(arNumeros[pos]+"");
                arBotones[pos].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventoCalcu(arNumeros[pos], this));
                arBotones[pos].setPrefSize(50,100);
                hBoxes[i].getChildren().add(arBotones[pos]);
                pos++;
            }
        }
        vBox.getChildren().addAll(txtOperacion, hBoxes[0], hBoxes[1], hBoxes[2], hBoxes[3], btnLimpiar);
        escena = new Scene(vBox,200,250);
        escena.getStylesheets().add(getClass().getResource("../css/StylesCalcu.css").toExternalForm());
    }

    public double realizarOperacion() { //Realiza la operacion que fue escrita entre los 2 numeros
        double resultado = 0;
        switch (operacion){
            case '+':
                resultado = num1 + num2;
                break;
            case '-':
                resultado = num1 - num2;
                break;
            case '*':
                resultado = num1 * num2;
                break;
            case '/':
                resultado = num1 / num2;
                break;
        }
        return resultado;
    }

    public void setNum1(String n){//guarda el primer numero escrito
        if(n.equals(".")) {// en caso de que el numero sea solo un punto lo convierte en 0
            num1 = 0;
        }else {
            num1 = Double.parseDouble(n);
        }
    }

    public void setNum2(String n){//guarda el segundo numero escrito
        if(n.equals(".")) {// en caso de que el numero sea solo un punto lo convierte en 0
            num2 = 0;
        }else {
            num2 = Double.parseDouble(n);
        }
    }

    private void limpiar() {//Limpia el campo de texto y resetea el valor de la operacion
        setContadorOperandos(0);
        txtOperacion.setText("");
    }

    public void setOperacion(char o){
        operacion = o;
    }

    public void setContadorOperandos(int c){
        contadorOperandos = c;
    }

    public int getContadorOperandos(){
        return contadorOperandos;
    }

    public void setTxtOperacion(char c){
        txtOperacion.setText(txtOperacion.getText() + c);
    }

    public void mostrarResultado(double r){
        txtOperacion.setText(String.valueOf(r));
    }

    public void setPosNum2(int p){
        posNum2 = p;
    }

    public int getPosNum2(){
        return posNum2;
    }

    public String getTxtOperacion(){
        return txtOperacion.getText();
    }

}

class EventoCalcu implements EventHandler{
    char tecla;
    Calculadora calcu;

    public EventoCalcu(char tecla, Calculadora calcu){
        this.tecla = tecla;
        this.calcu = calcu;
    }

    @Override
    public void handle(Event event) {
        int asciiValue = tecla;

        if(asciiValue >= 48 && asciiValue <= 57){// Si la tecla presionada es un numero lo escribe
            calcu.setTxtOperacion(tecla);
        }else{//si la tecla precionada no es un numero se toma una decicion de que hacer en cada caso
            switch (tecla){
                case '.':
                    if(calcu.getContadorOperandos() == 0) {
                        if(!calcu.getTxtOperacion().contains(".")) {
                            calcu.setTxtOperacion(tecla);//si no se ha ingresado una operacion y no hay otro punto, lo escribe
                        }
                    }else {
                        if(!calcu.getTxtOperacion().substring(calcu.getPosNum2()).contains(".")) {
                            calcu.setTxtOperacion(tecla);//si el numero 2 no contiene ningun punto, se escribe
                        }
                    }
                    break;
                case '-'://escribe el - solo si es posible respecto a la forma de escribir operaciones
                    if (calcu.getTxtOperacion() == ""){
                        calcu.setTxtOperacion(tecla);
                    }else{
                        if(calcu.getTxtOperacion().length() == calcu.getPosNum2() || calcu.getContadorOperandos() == 0){
                            if (restriccionOperacionMenos()){
                                if(calcu.getTxtOperacion().charAt(calcu.getTxtOperacion().length()-1) != '*'){
                                    if(calcu.getTxtOperacion().charAt(calcu.getTxtOperacion().length()-1) != '/'){
                                        calcu.setNum1(calcu.getTxtOperacion());
                                        calcu.setPosNum2(calcu.getTxtOperacion().length()+1);
                                        calcu.setContadorOperandos(1);

                                    }
                                }
                                calcu.setTxtOperacion(tecla);
                                calcu.setOperacion(calcu.getTxtOperacion().charAt(calcu.getPosNum2()-1));
                            }
                        }
                    }
                    break;
                case '+':
                case '*':
                case '/':
                    if(!calcu.getTxtOperacion().equals("-")){//Escribe la operacion si las condiciones lo permiten
                        if(!calcu.getTxtOperacion().equals("")){
                            if(restriccionOperacion()){
                                if(calcu.getContadorOperandos() == 0){
                                    calcu.setNum1(calcu.getTxtOperacion());
                                    calcu.setContadorOperandos(1);
                                    calcu.setPosNum2(calcu.getTxtOperacion().length()+1);
                                    calcu.setTxtOperacion(tecla);
                                    calcu.setOperacion(calcu.getTxtOperacion().charAt(calcu.getPosNum2()-1));
                                }
                            }
                        }
                    }
                    break;
                case '='://Muestra el resultado si ya se han ingresado los 2 numeros y la operacion
                    if(calcu.getContadorOperandos() == 1){
                        if(!calcu.getTxtOperacion().substring(calcu.getPosNum2()).equals("")){//obtiene el simbolo de la operacion
                            calcu.setNum2(calcu.getTxtOperacion().substring(calcu.getPosNum2()));
                            calcu.mostrarResultado(calcu.realizarOperacion());
                        }
                    }
            }
        }
    }

    boolean restriccionOperacion(){// verifica si el =,/ o * pueden ser escritos
        boolean b;
        char aux = calcu.getTxtOperacion().charAt(calcu.getTxtOperacion().length()-1);
        if (aux != '*' && aux != '/' && aux != '+' && aux != '-'){
            b = true;
        }else{
            b = false;
        }
        return b;
    }

    boolean restriccionOperacionMenos(){// Verifica si el - puede ser escrito
        boolean b;
        char aux = calcu.getTxtOperacion().charAt(calcu.getTxtOperacion().length()-1);
        if (aux != '+' && aux != '-'){
            b = true;
        }else{
            b = false;
        }
        return b;
    }
}