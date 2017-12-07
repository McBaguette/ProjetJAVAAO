package controller;

import javafx.scene.image.Image;
import graphe.Edge;

/**
 * Created by clement on 01/12/2017.
 */

import graphe.Labyrinth;
import graphe.Vertex;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DefineClass;
import model.Game;
import model.IDeplacable;
import model.DefineClass.Type;
import model.mapobject.Candy;
import model.mapobject.IMapObject;
import model.mapobject.Switch;
import view.Images;
import view.Sprite;
import view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * class ControllerView: in this class, we call function to refresh the View
 */
public class ControllerView {
    private static ControllerView instance = new ControllerView();
    private Game game;
    private View view;
    private Sprite imageViewPlayer;
    private Sprite imageViewDoor;
    private List<Sprite> listImageViewEnemies;
    private HashMap<String,Image> hashItemsMap;
    private HashMap<IMapObject,Sprite> hashViewMap;

    private ControllerView(){
        game = Game.getInstance();
        view = View.getInstance();
        listImageViewEnemies = new LinkedList<>();
        hashItemsMap = new HashMap<String,Image>();    
        hashViewMap = new HashMap<IMapObject,Sprite>();
        
    }

    /**
     * Call at the beginnig of a new level, call only one time at every level,
     * because it's not necessary to refresh wall during the game.
     * @param lab
     */
    private void initializeWallView(Labyrinth lab){
        for(Vertex v :lab.vertexSet()){
            for (DefineClass.Directions dir: DefineClass.Directions.values()){
                if (lab.isWall(v, dir)){
                    int x = v.getX();
                    int y = v.getY();
                    switch(dir){
                        case EAST:
                            x ++;
                            break;
                        case NORTH:
                            y --;
                            break;
                        case SOUTH:
                            y ++;
                            break;
                        case WEST:
                            x --;
                            break;
                    }
                    Vertex tmpVertex = new Vertex(x,y);
                    if (tmpVertex.inBorders())
                        View.getInstance().drawWall(v.getX(), v.getY(), x, y);
                }

            }
        }
    }
    
    private void drawDoors(Labyrinth lab){
        for(Edge e :lab.edgeSet()){
        	Type type = e.getType();
        	if(type == Type.CLOSED_DOOR || type == Type.OPENED_DOOR ) {
        		View.getInstance().drawDoor(e.getSource().getX(), e.getSource().getY(), e.getTarget().getX(), e.getTarget().getY(), type==Type.OPENED_DOOR);
        	}
        }
    }

    /**
     * Call at the begining of a nez level to create imageViews of the player and the enemies.
     */
    private void loadImageViewsDeplacable(){
        if (imageViewPlayer == null){
            imageViewPlayer = new Sprite(Images.imagePlayer);
            imageViewPlayer.addImageToView(view.getPane());
        }

        while(listImageViewEnemies.size() > game.getEnemies().size()){
        	listImageViewEnemies.get(0).removeImageFromView(view.getPane());
            listImageViewEnemies.remove(0);
        }
        while (listImageViewEnemies.size() < game.getEnemies().size()){
            Sprite sprite = new Sprite(Images.imageEnemy);
            listImageViewEnemies.add(sprite);
            sprite.addImageToView(view.getPane());
        }

    }

    /**
     * Call at the begining of a new level to create all imageViews we need
     */
    private void loadImageViews(){
        loadImageViewsDeplacable();
        if (imageViewDoor == null){
            imageViewDoor = new Sprite(Images.imageDoor);
            imageViewDoor.addImageToView(view.getPane());
        }
        for (int i = 0; i < DefineClass.NUMBER_CANDIES_TYPE; i++){
            hashItemsMap.put("Candy"+i, Images.imagesCandies[i]);
        }
        hashItemsMap.put("Switch_open", Images.imageButtonOpen);
        hashItemsMap.put("Switch_close", Images.imageButtonClose);
    }

    /**
     * Called at the beginning, Called by ControllerTimer.
     * @param primaryStage
     * @param laby
     */
    public void launch(Stage primaryStage, Labyrinth laby){
        view.launch(primaryStage, DefineClass.WIDTH, DefineClass.HEIGHT);
        restart(laby);
    }
    public void restart(Labyrinth laby){
        hashViewMap.clear();
        view.clear();
        imageViewPlayer = null;
        imageViewDoor = null;
        listImageViewEnemies.clear();
        loadImageViews();
        initializeWallView(laby);
    }

    /**
     * Called by ControllerTimer, will call View.drawImageView, to refresh the window
     * @param laby : Labyrinth, so the map of the game
     * @param player : IDeplacable, the player
     * @param enemies : List<IDeplacable>, all the enemies
     */
    public void refreshView(Labyrinth laby, IDeplacable player, List<IDeplacable> enemies){
    	drawDoors(laby);
        for(Vertex v: game.getLabyrinth().vertexSet()){
            List<IMapObject> listMapObject = v.getMapObjects();
            for (IMapObject o: listMapObject){
            	if(o.getName() != null && !hashViewMap.containsKey(o)) {
            		Sprite sprite = new Sprite(hashItemsMap.get(o.getName()));
            		hashViewMap.put(o, sprite);
            		sprite.addImageToView(view.getPane());
            	}else if(o.getName() == null) {
            		Sprite sprite = hashViewMap.get(o);
            		if(sprite!=null){
            			hashViewMap.remove(o);
            			sprite.removeImageFromView(view.getPane());
            		}
            	}
                Sprite sprite = hashViewMap.get(o);
                
                if (sprite != null)
                	sprite.draw(v.getX(), v.getY());
            }
            //Update if some objects disappeared from the map
        }
        imageViewPlayer.draw(game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY());
        imageViewDoor.draw(game.getVertexDoor().getX(), game.getVertexDoor().getY());
        
        int i = 0;
        for (IDeplacable e:enemies){
        	listImageViewEnemies.get(i).draw(e.getPosition().getX(), e.getPosition().getY());
            i ++;
        }

    }

    public static ControllerView getInstance(){
        return instance;
    }

}
