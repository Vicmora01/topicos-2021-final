package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.views.Calculadora;
import sample.views.Encriptador;
import sample.views.Rompecabezas;

public class Main extends Application implements EventHandler<WindowEvent> {

    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menCerrar;
    private MenuItem mitCalcu,mitRompecabezas, mitSalir, mitEncriptador, mitBDCanciones, mitCorredores, mitSocket;
    private Scene escena;

    //Práctica en la implementacion de Paneles en JavaFX
    private BorderPane borderPane;
    private FlowPane flowPane;
    private GridPane gridPane;
    private AnchorPane anchorPane;
    private Button buton1, buton2, buton3;

    @Override
    public void start(Stage primaryStage) throws Exception{
        vBox = new VBox();
        CrearMenu();
        CrearEscena();

        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,this );
        primaryStage.setTitle("Proyecto de clase Topicos avanzados de programacion 2021");
        primaryStage.setScene(escena);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void CrearMenu(){
        mnbPrincipal = new MenuBar();
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia2 = new Menu("Competencia 2");
        menCerrar = new Menu("Cerrar");
        mnbPrincipal.getMenus().addAll(menCompetencia1, menCompetencia2, menCerrar);
        //Práctica en la implementacion de Paneles en JavaFX
        buton1 = new Button("Boton 1");
        buton2 = new Button("Boton 2");
        buton3 = new Button("Boton 3");
        buton1.setStyle("-fx-background-color:purple;");
        buton2.setStyle("-fx-background-color:green;");
        buton3.setStyle("-fx-background-color:yellow;");
        buton1.setPrefSize(50,70);
        buton2.setPrefSize(90,40);
        buton3.setPrefSize(80,100);


        mitCalcu = new MenuItem("Calculadora");
        mitCalcu.setOnAction(event -> opcionesMenu(1));
        mitRompecabezas = new MenuItem("Rompecabezas");
        mitRompecabezas.setOnAction(event -> opcionesMenu(2));
        mitEncriptador = new MenuItem("Encriptador");
        mitEncriptador.setOnAction(event -> opcionesMenu(3));
        mitBDCanciones = new MenuItem("BDCanciones");
        mitBDCanciones.setOnAction(event -> opcionesMenu(4));

        menCompetencia1.getItems().addAll(mitCalcu, mitRompecabezas, mitEncriptador,mitBDCanciones);

        mitCorredores = new MenuItem("Ejecuciond e hilos");
        mitCorredores.setOnAction(event -> opcionesMenu(5));

        mitSocket = new MenuItem("Manejo de Sockets");
        mitSocket.setOnAction(event -> opcionesMenu(6));
        menCompetencia2.getItems().addAll(mitCorredores, mitSocket);

        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction(event -> {System.exit(0);});
        menCerrar.getItems().add(mitSalir);
    }

    private void CrearEscena(){
        vBox.getChildren().add(mnbPrincipal);

        //Práctica en la implementacion de Paneles en JavaFX

        //BorderPane comentar y descomentar uno por uno
        implementarBorderPane();
        vBox.getChildren().add(borderPane);
        escena = new Scene(vBox,500,400);

        //FlowPane Horizontal
        /*implementarFlowPaneHorizontal();
        vBox.getChildren().add(flowPane);
        escena = new Scene(vBox,500,400);*/

        //FlowPane Vertical
        /*implementarFlowPaneVertical();
        vBox.getChildren().add(flowPane);
        escena = new Scene(vBox,500,400);*/

        //GridPane
        /*implementarGridPane();
        vBox.getChildren().add(gridPane);
        escena = new Scene(vBox,500,400);*/

        //AnchorPane
        /*implementarAnchorPane();
        vBox.getChildren().add(anchorPane);
        escena = new Scene(vBox,500,400);*/

        escena.getStylesheets().add(getClass().getResource("css/Styles.css").toExternalForm());
    }

    private void opcionesMenu(int opc) {
        switch (opc){
           case 1: new Calculadora(); break;
            case 2: new Rompecabezas(); break;
            case 3: new Encriptador(); break;
            //case 4: new FrmCanciones(); break;
            //case 5: new Pista(); break;
            //case 6: new ClienteSocket().connectToServer();
        }
    }

    //Práctica en la implementacion de Paneles en JavaFX
    private void implementarBorderPane() {
        borderPane = new BorderPane();
        borderPane.setTop(buton1);
        borderPane.setBottom(buton2);
        borderPane.setRight(buton3);
    }

    private void implementarFlowPaneVertical() {
        flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.getChildren().addAll(buton1, buton2, buton3);
    }

    private void implementarFlowPaneHorizontal() {
        flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.getChildren().addAll(buton1, buton2, buton3);
    }

    private void implementarGridPane() {
        gridPane = new GridPane();
        gridPane.add(buton1, 0, 0);
        gridPane.add(buton2, 1, 0);
        gridPane.add(buton3, 3, 2);
    }

    private void implementarAnchorPane() {
        anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(buton1,30.0);
        AnchorPane.setRightAnchor(buton1,20.0);
        AnchorPane.setBottomAnchor(buton2,70.0);
        AnchorPane.setLeftAnchor(buton2,100.0);
        AnchorPane.setBottomAnchor(buton3,90.0);
        AnchorPane.setLeftAnchor(buton3,30.0);
        anchorPane.getChildren().addAll(buton1,buton2,buton3);
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent windowEvent) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del sistema");
        alerta.setHeaderText("Gracias por usar el programa :D");
        alerta.setContentText("Vuelva Pronto");
        alerta.showAndWait();
    }
}
