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
    
    public HBox getCadena(){
        return cadena;
    }
    
    public void setSelected(boolean selected){
        this.selected.setValue(selected);
    }
    
    protected Timeline getTimeLine(){
        return tl;
    }
        
    protected double getAncho(){
        return ancho;
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
    
    
}
