package model.mapobject;

import graphe.Vertex;
import model.DefineClass;
import view.Images;
import view.Sprite;

public class Candy extends MapObject{
	boolean isTook;
	private int wich;
	
    public Candy(int wich){

        this.isTook = false;
        this.wich = wich;
        if (wich < 0)
            wich = 0;
        else if (wich > DefineClass.NUMBER_CANDIES_TYPE)
            wich = DefineClass.NUMBER_CANDIES_TYPE-1;
        sprite = new Sprite(Images.imagesCandies[wich]);
        score = 10;
    }

    public void doAction() {
    	if(!isTook)
    		isTook = true;
    }

    @Override
    public String getName() {
        if(!isTook)
            return "Candy"+wich;
        return null;
    }
}
