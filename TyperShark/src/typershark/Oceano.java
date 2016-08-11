/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import Personajes.Animal;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sonidos.Reproductor;

/**
 *
 * @author Dario Ntn Carpio
 */
public class Oceano {
    private static ArrayList<Animal> animales;
    private static ArrayList<String> palabras;
    private static Pane animals;
    
    public Oceano(){
        animales = new ArrayList<Animal>();
        animals = new Pane();
        cargarPalabras();
    }
    
    public boolean haySeleccionado(){
        for (Animal a:animales){
            if (a.isSelected().getValue()){
                return true;
            }
        }
        return false;
    }
    
    public void addAnimal(Animal animal){
        actualizarOceano();
        System.out.println("addAnimal");      
        animales.add(animal);    
        animal.start();
    }
    
    public static void actualizarOceano(){
        Iterator<Animal> it = animales.iterator();
        while (it.hasNext()){
            Animal a = it.next();
            if (!a.getVida()){
                it.remove();
                //animals.getChildren().remove(a.getCuerpo());
                System.out.println("removido");
            }
        }
    }
    
    public Animal getSeleccionado(){
        for (Animal a:animales){
            if (a.isSelected().getValue()){
                return a;
            }
        }
        return null;
    }
    
    public void selecAnimal(String letraInicial){
        animales.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Animal a1 = (Animal)o1;
                Animal a2 = (Animal)o2;
                return Double.compare(a1.getPosicionX(),a2.getPosicionX());
            }
        });
        for (Animal a:animales){
            a.seleccionar(letraInicial);            
            if (a.isSelected().getValue()){
                return;
            }
        }
        Reproductor.play("error.mp3",0.9);
    }
    
    public Pane getAnimales(){
        for (Animal a:animales){
            animals.getChildren().add(a.getCuerpo());
        }
        return animals;
    }
    
    private void cargarPalabras() {
        File archivo = new File("palabras.txt");
        palabras = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(archivo);
            while (sc.hasNext()) {
                String word = sc.nextLine();
                palabras.add(word);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static String getPalabraRandom(){
        int random = (int)(Math.random()*(palabras.size()-1));
        return palabras.get(random);
    }
}
