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
    public boolean move(Labyrinth labyrinth){      //labyrinth with vertex marked with Manahatan algorithm
        for(DefineClass.Directions dir : DefineClass.Directions.values()) {
            Vertex next = (Vertex) Game.getInstance().getLabyrinth().getNeighborVertex(position, dir);
            if (next != null) { //it can be null, if next is on labyrinth's limits
                Edge edge = (Edge) Game.getInstance().getLabyrinth().getEdge(position, next);
                // Je préfere ne pas modifier du code de quelqu'un d'autre sans permission -
                // Il vaudrait pas mieux utiliser les méthode de labyrinth
                // qui donne l'état d'un Edge à partir du sommet actuel et de la direction ?
                // genre ici :
                // labyrinth.isNonBlocking(position, dir)
                // J'ai ptetre tout faux, si c'est le cas désolé, je restaurerais le isTraversable ! :p
                if (edge.isTraversable() && position.getNbr() == next.getNbr()-1){
                    setPosition(next);
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
