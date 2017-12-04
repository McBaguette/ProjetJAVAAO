package model.mapobject;

import graphe.Vertex;

public class Candy implements IMapObject{
	boolean isTook;
	
    public Candy(){
    	this.isTook = false;
    }

    public void doAction() {
    	if(!isTook) {
    		isTook = true;
    	}
    }

    @Override
    public String getName() {
        if(!isTook) return "Candy1";
        else return null;
    }
}
