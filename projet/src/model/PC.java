package model;


import graphe.Edges;
import graphe.Vertex;
import javafx.scene.image.Image;

public class PC extends Deplacable{
    public PC(Vertex position, Image image){
        super(position, image);
    }
    public boolean move(DefineClass.Directions direction){
        Vertex to = (Vertex) Game.getInstance().getLabyrinth().getNeighborVertex(position, direction);
        if (to == null)
            return false;
        Edges edge = (Edges) Game.getInstance().getLabyrinth().getEdge(position,to);

        if (edge.isTraversable()){
            setPosition(to);
            return true;
        }
        return false;
    }

}
