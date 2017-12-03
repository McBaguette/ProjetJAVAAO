package controller;

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
import model.mapobject.Candy;
import model.mapobject.IMapObject;
import view.Images;
import view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * class ControllerView: in this class, we call function to refresh the View
 */
public class ControllerView {
    private static ControllerView instance = new ControllerView();
    private Game game;
    private View view;
    private ImageView imageViewPlayer;
    private ImageView imageViewDoor;
    private List<ImageView> listImageViewEnemies;
    private List<ImageView>[][] arrayListImageViewItemsMap;

    private ControllerView(){
        game = Game.getInstance();
        view = View.getInstance();
        listImageViewEnemies = new LinkedList<>();
        arrayListImageViewItemsMap = new LinkedList[DefineClass.WIDTH][DefineClass.HEIGHT];
        for (int y = 0;  y < DefineClass.HEIGHT; y++){
            for (int x = 0; x < DefineClass.WIDTH; x++){
                arrayListImageViewItemsMap[x][y] = new LinkedList<>();
            }
        }
    }

    /**
     * Callat the beginnig of a new level, call only one time at every level,
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
                        View.getInstance().drawWall(v.getX(), v.getY(), x, y, Images.paintWall);
                }

            }
        }
    }

    /**
     * Call at the begining of a nez level to create imageViews of the player and the enemies.
     */
    private void loadImageViewsDeplacable(){
        if (imageViewPlayer == null){
            imageViewPlayer = new ImageView(Images.imagePlayer);
            view.addImageView(imageViewPlayer);
        }

        while(listImageViewEnemies.size() > game.getEnemies().size()){
            view.removeImageView(listImageViewEnemies.get(0));
            listImageViewEnemies.remove(0);
        }
        while (listImageViewEnemies.size() < game.getEnemies().size()){
            ImageView img = new ImageView(Images.imageEnemy);
            listImageViewEnemies.add(img);
            view.addImageView(img);
        }

    }

    /**
     * Call at the begining of a new level to create all imageViews we need
     */
    private void loadImageViews(){
        loadImageViewsDeplacable();
        imageViewDoor = new ImageView(Images.imageDoorOpen);
        view.addImageView(imageViewDoor);
        for (Vertex v: game.getLabyrinth().vertexSet()){
            for (IMapObject o: v.getMapObjects()){
                if (o instanceof Candy){
                    ImageView img = new ImageView(Images.imageCandy);
                    img.setId(Candy.getInstance().getName());
                    arrayListImageViewItemsMap[v.getX()][v.getY()].add(img);
                    view.addImageView(img);
                }
            }
        }

    }

    /**
     * Called at the beginning, Called by ControllerTimer.
     * @param primaryStage
     * @param laby
     */
    public void launch(Stage primaryStage, Labyrinth laby){
        view.launch(primaryStage, DefineClass.WIDTH, DefineClass.HEIGHT);
        initializeWallView(laby);
        loadImageViews();

    }

    /**
     * Called by ControllerTimer, will call View.drawImageView, to refresh the window
     * @param laby : Labyrinth, so the map of the game
     * @param player : IDeplacable, the player
     * @param enemies : List<IDeplacable>, all the enemies
     */
    public void refreshView(Labyrinth laby, IDeplacable player, List<IDeplacable> enemies){

        for(Vertex v: game.getLabyrinth().vertexSet()){
            List<IMapObject> listMapObject = v.getMapObjects();
            for (IMapObject o: listMapObject){
                ImageView imgView = findImageViewArrayMapObject(v.getX(), v.getY(), o);
                if (imgView != null)
                    view.drawImageView(imgView, v.getX(), v.getY());
            }
            //Update if some objects disappeared from the map
            updateImageViewVertex(v);
        }
        view.drawImageView(imageViewPlayer, game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY());
        view.drawImageView(imageViewDoor, game.getVertexDoor().getX(), game.getVertexDoor().getY());
        int i = 0;
        for (IDeplacable e:enemies){
            view.drawImageView(listImageViewEnemies.get(i), e.getPosition().getX(), e.getPosition().getY());
            i ++;
        }

    }

    /**
     * find in the arrayMapObject, the imageView attached to the object o.
     * @param x : x in array
     * @param y : y in array
     * @param o : IMapObject
     * @return ImageView found
     */
    private ImageView findImageViewArrayMapObject(int x, int y, IMapObject o){
        for (ImageView obj: arrayListImageViewItemsMap[x][y]){
            if (obj.getId().equals(o.getName()))
                return obj;
        }
        return null;
    }

    /**
     * Called to check if some object disapeared from the map, so we don't need their ImageView anymore.
     * @param v check his list of objectMap.
     */
    private void updateImageViewVertex(Vertex v){
        while (arrayListImageViewItemsMap[v.getX()][v.getY()].size() != v.getMapObjects().size())
        {
            for (ImageView obj: arrayListImageViewItemsMap[v.getX()][v.getY()]){
                boolean quit = true;
                for (IMapObject o: v.getMapObjects() ){
                    if (obj.getId().equals(o.getName())){
                        quit = false;
                        break;
                    }
                }
                if (quit){
                    view.removeImageView(obj);
                    arrayListImageViewItemsMap[v.getX()][v.getY()].remove(obj);
                    break;
                }

            }
        }
    }

    public static ControllerView getInstance(){
        return instance;
    }

}
