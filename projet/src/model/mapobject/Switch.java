package model.mapobject;

import graphe.Edge;
import model.DefineClass.Type;
import view.Images;
import view.Sprite;

public class Switch extends MapObject{
	Edge door;
	
    public Switch(Edge door){
        this.door = door;
    }
    
    /**
	 * Action the switch and change the state of its linked Edge
	 * @return true is the Edge is now "OPENED_DOOR", else false.
	 */
    public void doAction() {
    	if(door.getType() == Type.CLOSED_DOOR)
    		door.setType(Type.OPENED_DOOR);
    	if(door.getType() == Type.OPENED_DOOR)
        	door.setType(Type.CLOSED_DOOR);
    	return;
    }

    @Override
    public String getName() {
        return "Switch_"+(door.getType()==Type.OPENED_DOOR?"open":"close");
    }
}
