package model;


import graphe.Edge;
import graphe.Vertex;
import javafx.scene.image.Image;

/**
 * Class for player in the game
 */
public class PC extends Deplacable{
    public PC(Vertex position, Image image){
        super(position, image);
    }

    /**
     * Try to move to a direction
     * @param direction
     * @return boolean      to say if it has moved
     */
    public boolean move(DefineClass.Directions direction){
        Vertex to = (Vertex) Game.getInstance().getLabyrinth().getNeighborVertex(position, direction);
        if (to == null)     //can be null if the vertex to is on labyrinth's limits
            return false;
        Edge edge = (Edge) Game.getInstance().getLabyrinth().getEdge(position,to);

        if (edge.isTraversable()){
            setPosition(to);
            return true;
        }
        return false;
    }

}
