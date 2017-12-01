package model.mapobject;

public class Switch extends MapObject{
    private Switch(){
        instance = new Switch();
    }

    @Override
    public String getName() {
        return "Switch";
    }
}
