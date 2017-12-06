package model.mapobject;


import view.ISprite;

public abstract class MapObject implements IMapObject{
    protected static IMapObject instance;
    protected int score = 0;
    protected ISprite sprite;

    public int getScore(){return score;}
}
