/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sonidos;

import java.net.URL;
import javafx.animation.Animation;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import typershark.TyperShark;

/**
 *
 * @author Dario Ntn Carpio
 */
public class Reproductor {
    
    public static void play(String archivo, double volumen){
        final URL resource = TyperShark.class.getResource("/sonidos/"+archivo);
        MediaPlayer player = new MediaPlayer(new Media(resource.toString()));        
        player.setVolume(volumen);
        player.play();        
    }
    
    public static void playIndefinite(String archivo, double volumen){
        final URL resource = TyperShark.class.getResource("/sonidos/"+archivo);
        MediaPlayer player = new MediaPlayer(new Media(resource.toString()));                 
        player.setCycleCount(Animation.INDEFINITE);
        player.setVolume(volumen);
        player.play();        
    }
}
