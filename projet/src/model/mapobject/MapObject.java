package model.mapobject;


public abstract class MapObject implements IMapObject{
    protected static IMapObject instance;
    public static IMapObject getInstance() {
        return instance;
    }
}
