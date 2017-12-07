package model.mapobject;


import view.ISprite;

public abstract class MapObject implements IMapObject{
    protected static IMapObject instance;
    protected int score = 0;

    public int getScore(){return score;}
}
