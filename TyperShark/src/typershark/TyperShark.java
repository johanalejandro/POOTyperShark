/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TyperShark extends Application {

    private static MainFrame mainFrame;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
    
    @Override 
    public void start(Stage stage) {
        Group root = new Group();
        mainFrame = new MainFrame(root);
        stage.setTitle("Typer Shark");
        stage.getIcons().add(new Image("/imagenes/icon.png"));
        stage.setResizable(false);
        stage.setWidth(Constantes.SCREEN_WIDTH + 2*Constantes.WINDOW_BORDER);
        stage.setHeight(Constantes.SCREEN_HEIGHT+ 2*Constantes.WINDOW_BORDER + Constantes.TITLE_BAR_HEIGHT);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        mainFrame.changeState(0);    // SPLASH = 0 
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
