/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 *
 * @author KevinOmar
 */
public class Fin {
    private Group group;
    private ImageView background,gameover;
    private Button  playAgain,quit, aceptar;
    private VBox botones,panel;
    private TextField text;
    private Timeline timeline;
    private boolean state;
    private int stateArg;
    private Label label,resultado;
    
    
    public Fin() {
        group = new Group();
        gameover = new ImageView(new Image("/imagenes/gameover.png"));
        backGround();
        group.getChildren().add(gameover);
        gameover.setTranslateX(-1000);
        state = true;
        stateArg = 0;
        iniciarGameOver();
        timeline.play();
        botones();
        panelGuardado();
        crearEventos();

    }
    
    public void guardarResultado(){
        String cadena = text.getText()+"/"+resultado.getText();
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter("archivos/scores.txt",true);
            pw = new PrintWriter(fichero);
            pw.println(cadena);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        } 
        
    }
    
    public void crearEventos(){
        quit.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Platform.exit();
                System.exit(0);
            }
        });
        playAgain.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                TyperShark.getMainFrame().startGame();
            }
        });
        aceptar.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                guardarResultado();
                TyperShark.getMainFrame().changeState(0);
                
                
            }
        });
    }
    
    public void botones(){
        playAgain = new Button("Play Again");
        quit = new Button("Quit");
        playAgain.setEffect(new DropShadow());
        quit.setEffect(new DropShadow());
        playAgain.setFont(new Font("Impact", 18));
        quit.setFont(new Font("Impact", 18));
        playAgain.setMinSize(120, 30);
        playAgain.setTooltip(new Tooltip("Volver a jugar"));
        quit.setTooltip(new Tooltip("Salir del juego"));
        quit.setMinSize(120, 30);
        botones = new VBox(playAgain,quit);
        botones.setSpacing(10);
        botones.setTranslateX(430);
        botones.setTranslateY(520);
        botones.setOpacity(0);
        group.getChildren().add(botones);
    }
    
    private void iniciarGameOver() {
        
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Constantes.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (state) {
                    stateArg++;
                    int center = Constantes.SCREEN_WIDTH / 2;
                    int offset = (int) (Math.cos(stateArg / 4.0) * (40 - stateArg) / 40 * center);
                    gameover.setTranslateX(center - gameover.getImage().getWidth() / 2 + offset);
                    gameover.setTranslateX(center - gameover.getImage().getWidth() / 2 - offset);
                    if (botones.getOpacity() < 1) {
                        botones.setOpacity(botones.getOpacity() + 0.05f);
                        panel.setOpacity(botones.getOpacity() + 0.05f);
                    }
                    if (stateArg == 40) {
                        state= false;
                    }
                    return;
                }
            }    
        });
        timeline.getKeyFrames().add(kf);
    }
    
    public void backGround(){
        background = new ImageView();
        background.setFocusTraversable(true);
        background.setImage(new Image("/imagenes/background3.png"));
        background.setFitWidth(Constantes.SCREEN_WIDTH);
        background.setFitHeight(Constantes.SCREEN_HEIGHT);
        group.getChildren().addAll(background);
    }
    
    public void panelGuardado(){
        resultado = new Label(String.valueOf(TyperShark.getMainFrame().getPuntaje()));
        resultado.setTextFill(Color.GREENYELLOW);
        resultado.setFont(new Font("Impact", 80));
        label = new Label("Puntaje Obtenido");
        label.setFont(new Font("Impact", 18));
        text = new TextField();
        text.setPromptText("Ingrese su nombre");
        aceptar = new Button("Aceptar");
        aceptar.setFont(new Font("Impact", 18));
        
        aceptar.setTooltip(new Tooltip("Guardar resultado"));
        panel = new VBox(label,resultado,text,aceptar);
        panel.setSpacing(5);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-border-color: black;-fx-border-insets: 5;"+
                                "    -fx-border-width: 5;" +
                                "    -fx-padding: 15 30 15 30;");
        panel.setTranslateX(75);
        panel.setTranslateY(240);
        panel.setOpacity(0);
        group.getChildren().addAll(panel);


        
    }
    
  

    public Group getRoot(){
        return group;
    }
}
    
