package model;

import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;
import javafx.scene.image.Image;
import view.Images;
import view.Sprite;

/**
 * Class for enemies in the game
 */
public class NPC extends Deplacable{
    private boolean flee;
    public NPC(){
        super();
    }
    public NPC(Vertex position){
        this();
        this.position = position;
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
        	    if (labyrinth.getNeighborVertex(position, dir).getNbr() < position.getNbr()){
                    setPosition(labyrinth.getNeighborVertex(position, dir));
                    return true;
                }

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
