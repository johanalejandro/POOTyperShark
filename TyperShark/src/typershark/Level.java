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
    public void actualizarVidas(int jugadorVidas){
        vidas.getChildren().clear();
        for ( int lifes = 0 ; lifes < jugadorVidas ; lifes++){
            Image image= new Image("/imagenes/life.png");
            ImageView content = new ImageView(image);
            this.vidas.getChildren().add(content);
        }
    }
    
    public void upLevel(){
        Nnivel.set(Nnivel.get()+1);
        oceano.setVelocidades(Nnivel.get());
        grupo = 1;
    }
    
    public BorderPane getRoot(){
        return root;
    }
    
    private void crearInfoPanel() {
        vidas = new HBox();
        Font f = new Font("Impact", 18);
        root.setLeft(oceano.getBuceador().getImagen());
        infoPanel = new HBox();
        infoPanel.setSpacing(30);
        nivelCaption = new Text(" NIVEL  ");
        nivelCaption.setFill(Constantes.CAPTION_COLOR);
        
        nivelCaption.setFont(f);
        nivel = new Text(" 1");
        nivel.setFont(f);
        nivel.setFill(Constantes.VALUE_COLOR);
        Nnivel.addListener((ov,n1,n0)->{
            if (n1.intValue()!=n0.intValue()){
                nivel.setText(""+n0.intValue());
            }
        });
        puntajeCaption = new Text(" SCORE  ");
        puntajeCaption.setFill(Constantes.CAPTION_COLOR);
        puntajeCaption.setFont(f);
        score = new Text(" 0");
        score.setFill(Constantes.VALUE_COLOR);
        score.setFont(f);
        oceano.getBuceador().getPuntaje().addListener((ov,n1,n0)->{
            if (n1.intValue()!=n0.intValue()){
                score.setText(""+n0.intValue());
                booster += (n0.intValue()-n1.intValue());
                if (booster>=1000){
                    System.out.println("\t-Booster Ready-");
                    anunciarBooster(true);
                }
            }
        });
        livesCaption = new Text(" VIDAS  ");
        livesCaption.setFill(Constantes.CAPTION_COLOR);
        livesCaption.setFont(f);
        ImageView logo = new ImageView(new Image("/imagenes/logo.png"));
        logo.setFitHeight(80);
        logo.setFitWidth(100);
        vidas.setSpacing(2);
        actualizarVidas(Constantes.MAX_LIVES);
        oceano.getBuceador().getVidas().addListener((ov,n1,n0)->{
            if (n1.intValue()!=n0.intValue()){
                actualizarVidas(n0.intValue());
                if (n0.intValue()==0){
                    System.out.println("\tFIN");
                    TyperShark.getMainFrame().changeState(2);
                }
            }
        });
        infoPanel.getChildren().addAll(logo, new HBox(nivelCaption,nivel),new HBox(puntajeCaption,score),new HBox(livesCaption,vidas));
        infoPanel.setStyle("-fx-border-color: black;-fx-border-insets: 5;"+
                            "    -fx-border-width: 5;" +
                            "    -fx-padding: 15 30 15 30;" +
                            "    -fx-background-color:" +
                            "    linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                            "    linear-gradient(#020b02, #3a3a3a)," +
                            "    linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%);" +
                            "    -fx-background-insets: 0,1,4;" +
                            "    -fx-background-radius: 9,8,5;"); 
        for (Node i: infoPanel.getChildren()){
            if ( !i.equals(logo)){
                i.setTranslateY(25);
            }
        }
        infoPanel.setPrefWidth(Constantes.SCREEN_WIDTH);
        root.setPrefHeight(Constantes.SCREEN_HEIGHT);
        root.setTop(infoPanel);
    }
   
    
    public void anunciarBooster(Boolean aviso){
        if (aviso){
            killer.setOpacity(1);
        }else{
            killer.setOpacity(0);
        }
    }
    
   
    
    @Override
    public void run(){                                        
        while (Nnivel.get() <= Constantes.MAX_LEVEL){
            while (grupo<=Constantes.GRUPOS_ANIMALESxNIVEL){
                if (oceano.isEmpty()){                
                    System.out.println("GRUPO "+grupo);                
                    Platform.runLater(new Runnable() {
                        @Override 
                        public void run() {
                            root.requestFocus();                        

                            for(int i=0;i<(int)(Math.random()*(Constantes.MAX_ANIMALESxGRUPO-Constantes.MIN_ANIMALESxGRUPO))+Constantes.MIN_ANIMALESxGRUPO;i++){
                                switch ((int)(Math.random()*3)){
                                    case 0:
                                        oceano.addAnimal(new TiburonNegro());
                                        break;
                                    case 1:
                                        oceano.addAnimal(new TiburonBlanco());
                                        break;
                                    case 2:
                                        oceano.addAnimal(new Piraña());
                                        break;
                                }
                            }
                            root.setCenter(oceano.getAnimales());
                        }
                    });                
                    grupo++;
                    try {
                        Level.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }               
            }
            if (oceano.isEmpty()){
                upLevel();
            }
        }
    }
  
    public String getScore() {
        return score.getText();
    }
}
