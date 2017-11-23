package model;


import graphe.Edges;
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
        for(DefineClass.Directions dir : DefineClass.Directions.values()) {
            Vertex next = (Vertex) Game.getInstance().getLabyrinth().getNeighborVertex(position, dir);
            if (next != null) { //it could be null, if next is on the limit of the labyrinth
                Edges edge = (Edges) Game.getInstance().getLabyrinth().getEdge(position, next);
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
