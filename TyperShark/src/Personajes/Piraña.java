/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import typershark.Constantes;

/**
 *
 * @author Dario Ntn Carpio, Johan A Canales, Kevin O Palacios
 */
public class Piraña extends Animal{
    private final int puntos = 55;    
    
    public Piraña(){        
        super(Constantes.VEL_PIRAÑA);
        int x, y;
        x = (int)(Math.random()*250) + 620;
        y = (int)(Math.random()*150) + 5;
        this.setPuntos(puntos);
        this.setPosicionInicial(x, y);
        this.setAnimal(getLetraRandom(), new ImageView(new Image("/imagenes/piraña.png",Constantes.WIDTH_PIRAGNE,Constantes.HEIGHT_PIRAGNE,true,true)));
    }
    
    private String getLetraRandom(){
        int random = (int)(Math.random()*25)+97;
        return ""+(char)random;
    }
    
    @Override
    public int getPuntos() {
        return puntos;
    }
    
    @Override
    protected double getLimite() {
        return -360;
    }
}
