package typershark;

import Personajes.Buceador;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sonidos.Reproductor;

/**
 *
 * @author KevinOmar
 */
public class MainFrame {
    public static final int SPLASH = 0;
    private int state = SPLASH;
    private Group root;
    private Intro splash;
    private int lifeCount;
    private int puntaje;
    private HBox infoPanel;
    private HBox vidas;
    private ImageView background;
    private Level level ;
    private Buceador  jugador;

    MainFrame(Group root) {
        this.root = root;
    }


    public void startGame() {
        jugador = new Buceador();
        puntaje = jugador.getPuntaje().get();
        changeState(1);
    }



    public void changeState(int newState) {
        this.state = newState;
        if (splash != null) {
            splash.stop();
        }

        if (state < 1) {

            splash = new Intro();
            root.getChildren().add(splash);
            splash.start();
        } 
        if(state==1) {
            level = new Level();
            level.start();
            root.getChildren().clear();
            splash = null;
            root.getChildren().add(level.getRoot());

        }
        if (state == 2){
            Fin gameOver = new Fin();
            level.stop();
            Reproductor.stop();
            root.getChildren().clear();
            level = null;
            root.getChildren().add(gameOver.getRoot());
        }
        if (state == 3){
            LeaderBoard leader = new LeaderBoard();
            root.getChildren().clear();
            root.getChildren().add(leader.getRoot());
        }
    }
