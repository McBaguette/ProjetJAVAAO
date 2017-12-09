package model;


import graphe.Vertex;

/**
 * Class for player in the game
 */
public class PC extends Movable {
    public PC(){
        super();
    }
    public PC(Vertex position){
        super(position);
    }

    /**
     * Try to move to a direction
     * @param direction, what direction the PC will moved, but check if it can
     * @return boolean      to say if it has moved
     */
    public boolean move(DefineClass.Directions direction){

    	if(Game.getInstance().getLabyrinth().isNonBlocking(position, direction)) {
    		setPosition(Game.getInstance().getLabyrinth().getNeighborVertex(position, direction));
            return true;
    	}
        return false;
    }

}
