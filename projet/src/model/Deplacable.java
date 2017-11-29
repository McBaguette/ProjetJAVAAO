package model;


import graphe.Vertex;
import javafx.scene.image.Image;

public abstract class Deplacable implements IDeplacable {

    protected Vertex position;
    public Deplacable(){position = null;}
    public Deplacable(Vertex position)
    {
        this.position = position;
    }
    public Vertex getPosition() {
        return position;
    }
    public void setPosition(Vertex to){position = to;
    }
}
