package model.mapobject;

import graphe.Edge;
import model.DefineClass.Type;

public class Switch extends MapObject{
	Edge door;
	
    public Switch(Edge door){
        this.door = door;
    }
    
    /**
	 * Action the switch and change the state of its linked Edge
	 * @return true is the Edge is now "OPENED_DOOR", else false.
	 */
    public boolean actionSwitch() {
    	if(door.getType() == Type.CLOSED_DOOR) {
    		door.setType(Type.OPENED_DOOR);
    		return true;
    	}
    	if(door.getType() == Type.OPENED_DOOR)
        		door.setType(Type.CLOSED_DOOR);
        return false;
    }

    @Override
    public String getName() {
        return "button";
        //_"+(door.getType()==Type.OPENED_DOOR?"open":"close");
    }
}
