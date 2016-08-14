/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import sonidos.Reproductor;
import typershark.Constantes;
import typershark.Oceano;

/**
 *
 * @author Dario Ntn Carpio
 */
public class TiburonNegro extends Animal{
    private final int puntos = 60;
    private int palabrasRestantes;
    
    public TiburonNegro(){        
        super(Constantes.VEL_TIBURON);
        int x, y;
        palabrasRestantes = (int)(Math.random()*2) + 2;
        x = (int)(Math.random()*250) + 600;
        y = (int)(Math.random()*Constantes.SCREEN_HEIGHT/2);
        this.setPosicionInicial(x, y);
        this.setAnimal(Oceano.getPalabraRandom(), new ImageView(new Image("/imagenes/blackshark.png",Constantes.WIDTH_SHARK,Constantes.HEIGHT_SHARK,true,true)));
    }
    
    @Override
    public boolean destruir(){
        if(getLetrasAcertadas()==this.getCadena().getChildren().size()){
            palabrasRestantes--;
            this.setLetrasAcertadas(0);
            this.setSelected(false);
            if (palabrasRestantes==0){
                this.setVida(false);
                System.out.println("Animal muerto");                
                Timeline tl = getTimeLine();
                tl = new Timeline();
                tl.setCycleCount(1);
                tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e->{
                        Oceano.actualizarOceano();
                    },
                    new KeyValue(this.getCuerpo().translateYProperty(),this.getCuerpo().getTranslateY()-30.05,Interpolator.SPLINE(0.295,0.800,0.305,1.000)),
                    new KeyValue(this.getCuerpo().opacityProperty(), 0f)
                ));
                tl.play();
                Reproductor.play("explosion.mp3",1);
                return true;
            }else{
                setPalabra(Oceano.getPalabraRandom());
            }
        }
        return false;
    }
    
    @Override
    public int getPuntos(){
        return puntos;
    }

    @Override
    protected double getLimite() {
        return -1*Constantes.SCREEN_WIDTH/2 +50;
    }

    
}
