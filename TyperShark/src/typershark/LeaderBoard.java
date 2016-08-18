/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 *
 * @author johanalejandro
 */
public class LeaderBoard {
    static Map<String, Integer> sortedMap;
private static Map<String, Integer> sortByValue(Map<String, Integer> mapaDesordenado) {

  
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(mapaDesordenado.entrySet());

        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o2,Map.Entry<String, Integer> o1
                               ) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

       

        return sortedMap;
    }

public static Map<String, Integer> crearMapa(){
   File archivo = new File("scores.txt");
    Map<String, Integer> mapaJugadores = new HashMap<String, Integer>();

        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                String  linea=sc.next();
                System.out.println(linea);
                String [] jugadorPuntaje=linea.split("\\|");
                int puntaje=Integer.parseInt(jugadorPuntaje[1]);
                mapaJugadores.put(jugadorPuntaje[0], puntaje);
            }
             
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        sortedMap= sortByValue(mapaJugadores);
        return sortedMap;
    
    
    
}

public void addScore(String nombrePlayer, int puntaje){
    if(!sortedMap.containsKey(nombrePlayer))
        try {
            sortedMap.put(nombrePlayer, puntaje);
            String linea = nombrePlayer+"|"+Integer.toString(puntaje);
            File file = new File("scores.txt");
            if (!file.exists()) 
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(linea);
            bw.close();
       } catch (IOException e) {
            e.printStackTrace();
                    }
    else{
        if(sortedMap.get(nombrePlayer)>puntaje)
                sortedMap.put(nombrePlayer, puntaje);
    }
        
    
    }
}
