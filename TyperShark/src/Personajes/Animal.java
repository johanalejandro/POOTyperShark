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
    
}
