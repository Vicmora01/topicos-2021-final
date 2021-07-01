package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Rompecabezas extends Stage implements EventHandler {

    private String[] arImagenes;
    private String[][] arAsignacion;
    private String dir;

    private BorderPane borderPane;
    private GridPane tablero;
    private Button[][] btnTarjetas;
    private HBox hBox;
    private Label lblTarjetas;
    private TextField txtTamano;
    private Button btnMezclar;
    private Scene escena;

    private int noPiezas;
    private boolean bandera = false; // indica si ya se selecciono una carta para el intercambio
    private int xTemp, yTemp = 0;

    public Rompecabezas(){
        CrearUI();
        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        borderPane = new BorderPane();
        lblTarjetas = new Label("Tamaño");
        txtTamano = new TextField();

        btnMezclar = new Button("Mezclar las tarjetas");
        btnMezclar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,txtTamano,btnMezclar);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        borderPane.setTop(hBox);

        tablero = new GridPane();
        borderPane.setCenter(tablero);

        escena = new Scene(borderPane, 900, 600);
        escena.getStylesheets().add(getClass().getResource("../css/StylesRompecabezas.css").toExternalForm());
    }

    @Override
    public void handle(Event event) {
        limpiarGridPane();

        // verifica si la entrada es valida si no, manda un mensaje al usuario
        if(txtTamano.getText().equals("3") || txtTamano.getText().equals("4") || txtTamano.getText().equals("5")) {
            noPiezas = Integer.parseInt(txtTamano.getText());
        }else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Rompecabezas");
            alerta.setHeaderText("Ingrese un numero del 3 al 5");
            alerta.setContentText("Intetar nuevamente");
            alerta.showAndWait();
        }

        btnTarjetas =  new Button[noPiezas][noPiezas];
        arAsignacion = new String[noPiezas][noPiezas];

        dir = noPiezas+"/";//guarda la direccion de la cual tomara las imagenes dependiendo el tamano seleccionado

        crearArImagenes();
        revolver();

        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                int finalI = i;
                int finalJ = j;

                btnTarjetas[i][j] = new Button();
                btnTarjetas[i][j].setPrefSize(600,500);

                agregarImagenBoton(i, j, i, j);

                btnTarjetas[i][j].setOnAction(event1 -> intercambio(finalI, finalJ));

                tablero.add(btnTarjetas[i][j],j,i);
            }
        }
    }

    private void agregarImagenBoton(int x, int y, int xAux, int yAux) {// agrega la imagen al boton correspondiente
        Image img = new Image("sample/assets/rompecabezas/"+dir+arAsignacion[x][y]);
        ImageView imv = new ImageView(img);
        imv.setFitHeight(120);
        imv.setPreserveRatio(true);
        btnTarjetas[xAux][yAux].setGraphic(imv);
    }

    private void limpiarGridPane() {// Elimina los botones del grid pane
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                tablero.getChildren().remove(btnTarjetas[i][j]);
            }
        }
    }

    private void crearArImagenes() {//Crea el arreglo de imagenes
        arImagenes = new String[noPiezas*noPiezas];

        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                arImagenes[(i * noPiezas) + j] = "fila-"+(i+1)+"-col-"+(j+1)+".jpg";
            }
        }
    }

    private void revolver(){// revuelve aleatoriamente las imagenes
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                arAsignacion[i][j] = new String();
            }
        }

        int posx, posy = 0;
        for (int i = 0; i < arImagenes.length;) {
            posx = (int)(Math.random()*noPiezas);
            posy = (int)(Math.random()*noPiezas);
            if(arAsignacion[posx][posy].equals("")){
                arAsignacion[posx][posy] = arImagenes[i];
                i++;
            }
        }
    }

    private void intercambio(int i, int j) {// Realiza el intercambio de de las imagenes
        if (!bandera){
            bandera = !bandera;
            xTemp = i;
            yTemp = j;
        }else{
            agregarImagenBoton(i, j, xTemp, yTemp);
            agregarImagenBoton(xTemp, yTemp, i, j);
            String aux = arAsignacion[i][j];
            arAsignacion[i][j] = arAsignacion[xTemp][yTemp];
            arAsignacion[xTemp][yTemp] = aux;
            bandera = !bandera;

            if (comprobarArmado()) {// En cada intercambio comprueba si el rompecabezas esta armado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Rompecabezas");
                alerta.setHeaderText("Felicidades has armado el rompecabezas");
                alerta.setContentText("Aceptar");
                alerta.showAndWait();
            }

        }
    }

    private boolean comprobarArmado() {//Comprueba si cada imagen esta en su lugar correspondiente
        boolean estaArmado = true;
        int i = 0, j;

        do {
            j = 0;
            do {
                String nombre = "fila-"+(i + 1)+"-col-"+(j + 1)+".jpg";
                if (!arAsignacion[i][j].equals(nombre)) {
                    estaArmado = false;
                }
                j++;
            }while(j < noPiezas && estaArmado);
            i++;
        }while(i < noPiezas && estaArmado);

        return estaArmado;
    }
}