package typershark;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Intro extends Parent {

    private static final int STATE_SHOW_TITLE = 0;
    private static final int STATE_SHOW_POO = 1;
    private static final int STATE_SUN = 2;
    private static final int STATE_BUTTONS = 3;
    private static final int SUN_AMPLITUDE_X = Constantes.SCREEN_WIDTH * 2 / 3;
    private static final int SUN_AMPLITUDE_Y = Constantes.SCREEN_WIDTH / 2;
    private ImageView background,typer,typerShadow,shark,sharkShadow,poo,pooShadow,sol;
    private Timeline timeline;
    private int state, stateArg;
    private ImageView[] NODES, NODES_SHADOWS;
    private Button play, leader, quit;
    private VBox botones;

    private void iniciarTimeLine() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Constantes.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (state == STATE_SHOW_TITLE) {
                    stateArg++;
                    int center = Constantes.SCREEN_WIDTH / 2;
                    int offset = (int) (Math.cos(stateArg / 4.0) * (40 - stateArg) / 40 * center);
                    typer.setTranslateX(center - typer.getImage().getWidth() / 2 + offset);
                    shark.setTranslateX(center - shark.getImage().getWidth() / 2 - offset);
                    if (stateArg == 40) {
                        stateArg = 0;
                        state = STATE_SHOW_POO;
                    }
                    return;
                }
                if (state == STATE_SHOW_POO) {
                    if (stateArg == 0) {
                        poo.setTranslateX(shark.getTranslateX() + typer.getImage().getWidth());
                        poo.setScaleX(0);
                        poo.setScaleY(0);
                        poo.setVisible(true);
                    }
                    stateArg++;
                    double coef = stateArg / 30f;
                    typer.setTranslateX(shark.getTranslateX()
                            + (shark.getImage().getWidth() - typer.getImage().getWidth()) / 2f * (1 - coef));
                    poo.setScaleX(coef);
                    poo.setScaleY(coef);
                    poo.setRotate((30 - stateArg) * 2);
                    if (stateArg == 30) {
                        stateArg = 0;
                        state = STATE_SUN;
                    }
                    return;
                }
                if (botones.getOpacity() < 1) {
                    botones.setOpacity(botones.getOpacity() + 0.05f);
                }
                stateArg--;
                double x = SUN_AMPLITUDE_X * Math.cos(stateArg / 100.0);
                double y = SUN_AMPLITUDE_Y * Math.sin(stateArg / 100.0);
                if (y < 0) {
                    for (Node node : NODES_SHADOWS) {
                        node.setTranslateX(-1000);
                    }
                    return;
                }
                double sunX = Constantes.SCREEN_WIDTH / 2 + x;
                double sunY = Constantes.SCREEN_HEIGHT / 2 - y;
                sol.setTranslateX(sunX - sol.getImage().getWidth() / 2);
                sol.setTranslateY(sunY - sol.getImage().getHeight() / 2);
                sol.setRotate(-stateArg);
                for (int i = 0; i < NODES.length; i++) {
                    NODES_SHADOWS[i].setOpacity(y / SUN_AMPLITUDE_Y / 2);
                    NODES_SHADOWS[i].setTranslateX(NODES[i].getTranslateX()
                            + (NODES[i].getTranslateX() + NODES[i].getImage().getWidth() / 2 - sunX) / 20);
                    NODES_SHADOWS[i].setTranslateY(NODES[i].getTranslateY()
                            + (NODES[i].getTranslateY() + NODES[i].getImage().getHeight() / 2 - sunY) / 20);
                }
            }
        });
        timeline.getKeyFrames().add(kf);
    }

    public void crearBotones() {
        play = new Button("Play");
        leader = new Button("LeaderBoard");
        quit = new Button("Quit");
        play.setEffect(new DropShadow());
        leader.setEffect(new DropShadow());
        quit.setEffect(new DropShadow());
        play.setFont(new Font("Impact", 18));
        quit.setFont(new Font("Impact", 18));
        leader.setFont(new Font("Impact", 18));
        play.setMinSize(120, 30);
        play.setTooltip(new Tooltip("Iniciar el juego"));
        leader.setTooltip(new Tooltip("Tabla de resultados"));
        quit.setTooltip(new Tooltip("Salir de la aplicacion"));
        leader.setMinSize(120, 30);
        quit.setMinSize(120, 30);
        play.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                TyperShark.getMainFrame().startGame();
            }
        });
        leader.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //TyperShark.getMainFrame().startGame();
            }
        });
        quit.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Platform.exit();
                System.exit(0);
            }
        });
        botones = new VBox(play, leader, quit);
        botones.setSpacing(10);
        botones.setTranslateX(430);
        botones.setTranslateY(520);
        botones.setOpacity(0);
    }

    public void start() {
        background.requestFocus();
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    Intro() {
        state = STATE_SHOW_TITLE;
        stateArg = 0;
        iniciarTimeLine();
        background = new ImageView();
        background.setFocusTraversable(true);
        background.setImage(new Image("/imagenes/background1.png"));
        background.setFitWidth(Constantes.SCREEN_WIDTH);
        background.setFitHeight(Constantes.SCREEN_HEIGHT);
        typer = new ImageView();
        typer.setImage(new Image("/imagenes/typer.png"));
        typer.setTranslateX(-1000);
        typer.setTranslateY(typer.getImage().getHeight());
        typerShadow = new ImageView();
        typerShadow.setImage(new Image("/imagenes/typershadow.png"));
        typerShadow.setTranslateX(-1000);
        shark = new ImageView();
        shark.setImage(new Image("/imagenes/shark2.png"));
        shark.setTranslateX(-1000);
        shark.setTranslateY(typer.getTranslateY() + typer.getImage().getHeight() * 5 / 4);
        sharkShadow = new ImageView();
        sharkShadow.setImage(new Image("/imagenes/sharkshadow.png"));
        sharkShadow.setTranslateX(-1000);
        poo = new ImageView();
        poo.setImage(new Image("/imagenes/poo.png"));
        poo.setTranslateY(typer.getTranslateY()
                - (poo.getImage().getHeight() - typer.getImage().getHeight()) / 2);
        poo.setVisible(false);
        pooShadow = new ImageView();
        pooShadow.setImage(new Image("/imagenes/pooshadow.png"));
        pooShadow.setTranslateX(-1000);
        sol = new ImageView();
        sol.setImage(new Image("/imagenes/sun.png"));
        sol.setTranslateX(-1000);
        crearBotones();
        NODES = new ImageView[]{typer, shark, poo};
        NODES_SHADOWS = new ImageView[]{typerShadow, sharkShadow, pooShadow};
        Group group = new Group();
        group.getChildren().add(background);
        group.getChildren().addAll(NODES_SHADOWS);
        group.getChildren().addAll(NODES);
        group.getChildren().addAll(sol, botones);
        getChildren().add(group);
    }

}
