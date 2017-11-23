package model;


import graphe.Vertex;
import javafx.scene.image.Image;

public interface IDeplacable {
    public Vertex getPosition();
    public Image getImage();
    public void setPosition(Vertex to);
}
