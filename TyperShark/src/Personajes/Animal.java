package Personajes;

import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * Clase Animal: representacion de los animales marinos del juego.
 * Con la cual los animales se pueden crear, mover y destruir.
 * @version 1.0
 * @author Johan Canales, Dario Carpio, Kevin Palacios
 */
public abstract class Animal extends Thread {
    
    
    private HBox cadena;
    private StackPane cuerpo;
    private SimpleBooleanProperty selected;    
    private int letrasAcertadas;
    private Timeline tl;
    private KeyFrame kf;
    private boolean vida;
    private int puntos;
    private int velocidad;
    private boolean cruzo;

    public Animal(int millis){
        super("animal");
        this.vida = true;
        cadena = new HBox();
        cuerpo = new StackPane();
        letrasAcertadas = 0;
        this.selected = new SimpleBooleanProperty(false);
        cadena.setAlignment(Pos.CENTER);
        velocidad = millis;
        cruzo = false;
    }
    
    public abstract int getPuntos();
    
    protected abstract double getLimite();
    
    public Pane getCuerpo(){
        return cuerpo;
    }
    public int getLetrasAcertadas(){
        return letrasAcertadas;
    }
    public void setLetrasAcertadas(int n){
        letrasAcertadas = n;
    }
    public boolean getVida(){
        return vida;
    }
    
    public void setVida(boolean vida){
        this.vida = vida;
    }
    
    public void setVelocidad(int millis){
        velocidad = millis;
    }
    
    public HBox getCadena(){
        return cadena;
    }
    
    public void setSelected(boolean selected){
        this.selected.setValue(selected);
    }
    
    protected Timeline getTimeLine(){
        return tl;
    }
    
    public boolean getCruzo(){
        return cruzo;
    }
        
    public void setAnimal(String palabra, ImageView imagen){
        crearPalabra(palabra);
        cuerpo.getChildren().addAll(imagen,cadena);
    }
    public final void crearPalabra(String palabra) {
        cadena = new HBox();
        cadena.setAlignment(Pos.CENTER);
        for (int i=0; i< palabra.length();i++ ){
            Label l = new Label(Character.toString(palabra.charAt(i)));
            l.setStyle("-fx-text-fill: white;-fx-font: bold 17 serif");
            cadena.getChildren().add(l);
        }
    }
    public Boolean validarLetra(int indice, String letra){
        if(((Label) this.cadena.getChildren().get(indice)).getText().equals(letra) ){
            Reproductor.play("tecleo.mp3",1);
            return true;
        }
        
        Reproductor.play("error.mp3",0.9);
        if (velocidad > 5){
            velocidad = velocidad-5;
        }
        return false;
    }
    
    public void pintarLetra(String l){
        for(Node letra: cadena.getChildren()){
            Label lab = (Label) letra;
            if (l.equals(lab.getText())){
                if (!(lab.getStyle().equals("-fx-text-fill: red;-fx-font: bold 17 serif"))){
                    letra.setStyle("-fx-text-fill: red;-fx-font: bold 17 serif");
                    return;
                }
            }
        }
    }
    public SimpleBooleanProperty isSelected(){
        return selected;
    }
    public void seleccionar(String letra){
        if( !this.cadena.getChildren().isEmpty()){
            if(letra.equals(((Label) this.cadena.getChildren().get(0)).getText())){
                if(!(this.isSelected().getValue())){
                    this.selected.set(true);                    
                }
            }            
        }
    }
    
    protected void setPalabra(String palabra){
        cuerpo.getChildren().remove(cadena);
        crearPalabra(palabra);
        cuerpo.getChildren().add(cadena);
    }
    public boolean destruir(){
        if(letrasAcertadas==this.cadena.getChildren().size()){            
            this.vida = false;
            System.out.println("Animal muerto");
            letrasAcertadas = 0;
            selected.set(false);
            
            tl = new Timeline();
            
            tl.setCycleCount(1);
            tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e->{
                    Oceano.actualizarOceano();
                },
                new KeyValue(cuerpo.translateYProperty(), cuerpo.getTranslateY()-30.05,Interpolator.SPLINE(0.295,0.800,0.305,1.000)),
                new KeyValue(cuerpo.opacityProperty(), 0f)
            ));            
            tl.play();
            Reproductor.play("explosion.mp3",1);
            return true;
        }
        return false;
    }
    
    
    @Override 
    public void run() {
        while (vida){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                    cuerpo.setTranslateX(cuerpo.getTranslateX()-3);
                    if(cuerpo.translateXProperty().lessThan(getLimite()).getValue()){
                        vida = false;
                        cruzo = true;
                        cuerpo.setOpacity(0.0);
                        Oceano.actualizarOceano();
                    }
                    
                }
            });
            
            try {
                Animal.sleep((int) velocidad);
            } catch (InterruptedException ex) {
                Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
