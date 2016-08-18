/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 *
 * @author johanalejandro
 */
public class LeaderBoard {
    private TableView<Map.Entry<String,Integer>> table;
    private ObservableList<Map.Entry<String, Integer>> items;
    private VBox vbox;
    private StackPane panel;
    private Button volver;
    private Map<String, Integer> map,sortedMap;
    private ImageView background;
    private TableColumn<Map.Entry<String, Integer>, String> nombreCol,puntajeCol;
    
    public Leaderboard(){
        cargar();
        crearTabla();
        crearBotones();
        darEstilo();   
        
        
        
    }
private static Map<String, Integer> sortByValue(Map<String, Integer> mapaDesordenado) {

  
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(mapaDesordenado.entrySet());

        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o2,Map.Entry<String, Integer> o1
                               ) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

       

        return sortedMap;
    }

public static Map<String, Integer> crearMapa(){
   File archivo = new File("scores.txt");
    Map<String, Integer> mapaJugadores = new HashMap<String, Integer>();

        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                String  linea=sc.next();
                System.out.println(linea);
                String [] jugadorPuntaje=linea.split("\\|");
                int puntaje=Integer.parseInt(jugadorPuntaje[1]);
                mapaJugadores.put(jugadorPuntaje[0], puntaje);
            }
             
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        sortedMap= sortByValue(mapaJugadores);
        return sortedMap;
    
    
    
}

public void addScore(String nombrePlayer, int puntaje){
    if(!sortedMap.containsKey(nombrePlayer))
        try {
            sortedMap.put(nombrePlayer, puntaje);
            String linea = nombrePlayer+"|"+Integer.toString(puntaje);
            File file = new File("scores.txt");
            if (!file.exists()) 
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(linea);
            bw.close();
       } catch (IOException e) {
            e.printStackTrace();
                    }
    else{
        if(sortedMap.get(nombrePlayer)>puntaje)
                sortedMap.put(nombrePlayer, puntaje);
    }
        
    
    }
    public void crearTabla(){
        Label label = new Label("LEADERBOARD");
        label.setStyle("-fx-font-size: 36pt;" +
                    "    -fx-font-family: \"Segoe UI Semibold\";" +
                    "    -fx-text-fill: DarkGreen;" );
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        nombreCol = new TableColumn<>("NOMBRES");
        nombreCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });
        puntajeCol = new TableColumn<>("PUNTAJE");
        puntajeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) {
                return new SimpleStringProperty(String.valueOf(p.getValue().getValue()));
            }
        });
        table = new TableView<>(items);
        table.getColumns().setAll(nombreCol,puntajeCol);
        table.setPrefHeight(500);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        background = new ImageView(new Image("/imagenes/background1.png"));
        background.setFocusTraversable(true);
        background.setFitWidth(Constantes.SCREEN_WIDTH);
        background.setFitHeight(Constantes.SCREEN_HEIGHT);
        vbox = new VBox();
        vbox.setPrefHeight(Constantes.SCREEN_HEIGHT -  75);
        panel = new StackPane(background,vbox);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(labelHb, table);
    }
    public void crearBotones(){
        volver = new Button("Back");
        volver.setEffect(new DropShadow());
        volver.setFont(new Font("Impact", 18));
        volver.setMinSize(120, 30);
        volver.setTooltip(new Tooltip("volver al Menu"));
        vbox.getChildren().add(volver);
        volver.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                TyperShark.getMainFrame().changeState(0);
                
                
            }
        });
        
    }
    public void darEstilo(){
        table.setStyle("-fx-font-size: 8pt;" +
                       "-fx-font-family: verdana;"+
                       "-fx-border-width: 0 0 0 0;" +
                       "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;"+
                        "-fx-background-color: transparent;"+
                        "-fx-control-inner-background: transparent;\n" +
                        "-fx-background-color: transparent;\n" +
                        "-fx-table-cell-border-color: transparent;\n" +
                        "-fx-table-header-border-color: transparent;\n"  );
        nombreCol.setStyle("-fx-font-size: 17pt;" +
                "    -fx-font-family: Impact;" +
                "    -fx-text-fill: Chartreuse ;" +
                "    -fx-alignment: BASELINE_CENTER;"+
                "    -fx-background-color: transparent;"
        );
        puntajeCol.setStyle("-fx-font-size: 17pt;" +
                "    -fx-font-family: Impact;" +
                "    -fx-text-fill: Chartreuse ;" +
                "    -fx-alignment: BASELINE_CENTER;"+
                "    -fx-background-color: transparent;");
       
    }
    
    public StackPane getRoot(){
        return panel;
    }
}
