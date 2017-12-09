package model;


import graphe.Vertex;
import javafx.scene.image.Image;
import view.ISprite;
import view.Sprite;

/**
 * Interface for enemies and the player, represent objects able to move on the game
 */
public interface IMovable
{
    public Vertex getPosition();
    public void setPosition(Vertex to);
}
