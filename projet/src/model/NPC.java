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
        	if(labyrinth.isNonBlocking(position, dir)) {
        		setPosition(labyrinth.getNeighborVertex(position, dir));
                return true;
        	}
        	/* Je le laisse ici pour l'instant, au cas o�
	            Vertex next = (Vertex) Game.getInstance().getLabyrinth().getNeighborVertex(position, dir);
	            if (next != null) { //it can be null, if next is on labyrinth's limits
	                Edge edge = (Edge) Game.getInstance().getLabyrinth().getEdge(position, next);
	                //En fait, au lieu de faire comme le prof, l'idée est d'avoir juste un méthode isTraversable (et chaque edges à un boolean traversable), puisqu'on a juste besoin de savoir
	                //* si on peut traverser. Et donc on s'en fiche de savoir si c'est un mur ou une porte ouvert/fermé un corridor … c'est plus simple de juste savoir si on peut passer. 
	                if (edge.isTraversable() && position.getNbr() == next.getNbr()-1){
	                    setPosition(next);
	                    return true;
	                }
	            }
        	 */
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
