package model;


import graphe.Vertex;
import javafx.scene.image.Image;

public class PC extends Deplacable{
    public PC(Vertex position, Image image){
        super(position, image);
    }
    public boolean move(DefineClass.Directions direction){
        return false;
    }

}
