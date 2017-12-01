package model.mapobject;

public class Candy extends MapObject{
    private Candy(){

    }
    public static IMapObject getInstance(){
        if (instance == null)
            instance = new Candy();
        return instance;
    }

    @Override
    public String getName() {
        return "Candy1";
    }
}
