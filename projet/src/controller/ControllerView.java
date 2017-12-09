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
import model.IMovable;
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
                        View.getInstance().drawWall(v.getX(), v.getY(), x, y, false, false);
                }

            }
        }
    }
    
    /**
      * Called by refreshview. In function of the edge type this method call the drawDoor method of the View.
      * @param lab 
      */
   
    private void drawDoors(Labyrinth lab){
        for(Edge e :lab.edgeSet()){
        	Type type = e.getType();
        	if(type == Type.CLOSED_DOOR || type == Type.OPENED_DOOR ) {
        		View.getInstance().drawWall(e.getSource().getX(), e.getSource().getY(), e.getTarget().getX(), e.getTarget().getY(), true, type==Type.OPENED_DOOR);
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
    
    /**
     * Called by launch to restart the view of the game.
     * It clear the view and image view of the game and launch the initializeWallView method to draw the labyrinth using the param laby.
     * param laby
    */
    
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
     */
    public void refreshView(){
    	drawDoors(game.getLabyrinth());
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
        }
        imageViewPlayer.draw(game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY());
        imageViewDoor.draw(game.getVertexDoor().getX(), game.getVertexDoor().getY());
        
        int i = 0;
        for (IMovable e:game.getEnemies()){
        	listImageViewEnemies.get(i).draw(e.getPosition().getX(), e.getPosition().getY());
            i ++;
        }

    }
    
    /**
     * Return the unique instance of this class.  
     */
    
    public static ControllerView getInstance(){
        return instance;
    }

}