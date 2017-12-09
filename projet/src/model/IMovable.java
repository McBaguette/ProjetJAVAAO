package model;


import graphe.Vertex;
import javafx.scene.image.Image;
import view.ISprite;
import view.Sprite;

public interface IMovable
{
    public Vertex getPosition();
    public void setPosition(Vertex to);
}
