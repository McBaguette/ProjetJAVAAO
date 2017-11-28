package model;

import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;
import javafx.scene.image.Image;

/**
 * Class for enemies in the game
 */
public class NPC extends Deplacable{
    private boolean flee;
    public NPC(Vertex position, Image image){
        super(position, image);
        flee = false;
    }

    /**
     * Move the NPC to the PC with Manhattan algorithm
     * @param labyrinth with vertex marked with Manhattan algorithm
     * @return boolean, to say if it has moved
     */
    public boolean move(Labyrinth labyrinth){      //labyrinth with vertex marked with Manahatan algorithm
        for(DefineClass.Directions dir : DefineClass.Directions.values()) {
        	if(labyrinth.isNonBlocking(position, dir)) {
        		setPosition(labyrinth.getNeighborVertex(position, dir));
                return true;
        	}
        }
        return false;
    }
    public boolean getFlee(){
        return flee;
    }
    public void setFlee(boolean b){
        flee = b;
    }
}
