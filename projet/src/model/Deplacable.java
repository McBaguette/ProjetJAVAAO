package model;


import graphe.Vertex;
import javafx.scene.image.Image;

public abstract class Deplacable implements IDeplacable {

    private Vertex position;
    private Image image;
    public Deplacable(Vertex position, Image image)
    {
        this.position = position;
        this.image = image;
    }
    public Vertex getPosition() {
        return position;
    }
    public javafx.scene.image.Image getImage() {
        return image;
    }
    public void setPosition(Vertex to){
        position = to;
    }
}
