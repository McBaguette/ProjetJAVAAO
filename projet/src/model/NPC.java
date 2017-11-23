package model;


import graphe.Labyrinth;
import graphe.Vertex;
import javafx.scene.image.Image;

public class NPC extends Deplacable{
    private boolean flee;
    public NPC(Vertex position, Image image){
        super(position, image);
        flee = false;
    }
    public boolean move(Labyrinth labyrinth){      //labyrinth with vertex marked with Manahatan algorithm
        return false;
    }
    public boolean getFlee(){
        return flee;
    }
    public void setFlee(boolean b){
        flee = b;
    }
}
