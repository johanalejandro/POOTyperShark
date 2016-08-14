package typershark;

import Personajes.*;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sonidos.Reproductor;

/**
 *
 * @author Dario N Cario, Kevin O Palacios, Johan A Canales
 */
public class Level extends Thread{

    protected BorderPane root;
    private Oceano oceano;
    private Animal elegido;
    private HBox infoPanel;
    private Text nivel,nivelCaption,lives,score,puntajeCaption,livesCaption;
    private HBox vidas;
    private SimpleIntegerProperty Nnivel;
    private int grupo;    
    private ImageView background;
    private int booster;
    private VBox killer;
    
    public Level(){
                
        root = new BorderPane();
        oceano = new Oceano();        
        Nnivel=new SimpleIntegerProperty(1);
        grupo = 1;
        booster = 0;
        this.crearBackground();
        root.setCenter(oceano.getAnimales());
        Reproductor.playIndefinite("music.mp3",0.8);
        root.requestFocus();
        this.crearInfoPanel();
        //Aviso del booster tecla ENTER
        Label Lbooster = new Label("Booster");
        Label ready = new Label("Ready!");
        Lbooster.setTextFill(Constantes.CAPTION_COLOR);
        ready.setTextFill(Constantes.CAPTION_COLOR);
        Lbooster.setFont( new Font("Impact", 18));
        ready.setFont( new Font("Impact", 18));
        killer = new VBox(Lbooster,ready);
        killer.setTranslateX(300);
        anunciarBooster(false);
        infoPanel.getChildren().add(killer);
        
        root.setOnKeyPressed(ke2 -> {
            if (ke2.getCode().compareTo(KeyCode.ENTER)==0 && (booster/Constantes.PUNTAJE_BOOSTER)>=1){
                booster = 0;
                System.out.println("\t-Booster Activated-");
                oceano.killThem();
                anunciarBooster(false);
            }
            if (!oceano.haySeleccionado()){
                oceano.selecAnimal(ke2.getText());
                elegido = oceano.getSeleccionado();
                if (elegido!=null){
                    elegido.escribirAnimal(ke2.getText());
                }
                
            }else{
                elegido.escribirAnimal(ke2.getText());
            }            
            ke2.consume();
        });
    }

    public void crearBackground(){
        background = new ImageView();
        background.setFocusTraversable(true);
        background.setImage(new Image("/imagenes/fondoMarino.gif"));
        background.setFitWidth(Constantes.SCREEN_WIDTH);
        background.setFitHeight(Constantes.SCREEN_HEIGHT);
        root.getChildren().add(background);
        
    }