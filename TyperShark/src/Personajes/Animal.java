/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

/**
 *
 * @author Dario Ntn Carpio
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
 
public class Animal extends Thread {
    
    
    private HBox cadena;
    private StackPane cuerpo;
    private SimpleBooleanProperty selected;    
    private int letrasAcertadas;
    private Timeline tl;
    private KeyFrame kf;
    private boolean vida;
    private int puntos;
    private int velocidad;
    private double ancho;
    
    public Animal(int millis){
        super("animal");
        this.vida = true;
        cadena = new HBox();
        cuerpo = new StackPane();
        letrasAcertadas = 0;
        this.selected = new SimpleBooleanProperty(false);
        cadena.setAlignment(Pos.CENTER);
        velocidad = millis;
        
    }
    public void setAnimal(String palabra, ImageView imagen){
        crearPalabra(palabra);
        ancho = imagen.getFitWidth();
        cuerpo.getChildren().addAll(imagen,cadena);
    }
    public final void crearPalabra(String palabra) {
        cadena = new HBox();
        cadena.setAlignment(Pos.CENTER);
        for (int i=0; i< palabra.length();i++ ){
            Label l = new Label(Character.toString(palabra.charAt(i)));
            l.setStyle("-fx-text-fill: white;-fx-font-size: 13;-fx-font-weight: bold");
            cadena.getChildren().add(l);
        }
    }
    
   
    
    public void pintarLetra(String l){
        for(Node letra: cadena.getChildren()){
            Label lab = (Label) letra;
            if (l.equals(lab.getText())){
                if (!(lab.getStyle().equals("-fx-text-fill: red;-fx-font-weight: bold;"))){
                    letra.setStyle("-fx-text-fill: red;-fx-font-weight: bold;");
                    return;
                }
            }
        }
    }
    
    
}
