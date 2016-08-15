/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import javafx.beans.property.SimpleIntegerProperty;
import typershark.Constantes;

/**
 *
 * @author KevinOmar
 */
public class Buceador {
    private SimpleIntegerProperty vidas;
    private SimpleStringProperty nombre;
    private SimpleIntegerProperty puntaje;
    private ImageView img;
    
    public Buceador(){
        vidas = new SimpleIntegerProperty(Constantes.MAX_LIVES);
        puntaje=new SimpleIntegerProperty(0);
        img = new ImageView(new Image("/imagenes/diver.gif"));
        img.setFitWidth(150);
        img.setFitHeight(70);
        img.resize(70, 20);
    }
    
    
    public ImageView getImagen(){
        return img;
    }

    public String getNombre() {
	
        return nombre.get();
    }
    public SimpleIntegerProperty getVidas() {
        return vidas;
    }
    
    public void setVida(int vida){
        vidas.set(vidas.get()+vida);
    }

    public void setPuntaje(int puntos) {
        puntaje.set(puntaje.get()+puntos);
    }

    public SimpleIntegerProperty getPuntajeProperty() {
        return puntaje;
    }
    
    
    public int getPuntaje() {
	
        return puntaje.get();
    }
    

}
