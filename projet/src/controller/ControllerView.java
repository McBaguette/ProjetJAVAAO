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
 * classe ControllerView: in this class, we call function to refresh the View
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

    private void loadImageViewsDeplacable(){
        if (imageViewPlayer == null){
            imageViewPlayer = new ImageView(Images.imagePlayer);
            view.addImageView(imageViewPlayer);
        }

        while (listImageViewEnemies.size() < game.getEnemies().size()){
            listImageViewEnemies.add(new ImageView(Images.imageEnemy));
            view.addImageView(listImageViewEnemies.get(listImageViewEnemies.size()-1));
        }

    }
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
    public void launch(Stage primaryStage, Labyrinth laby){
        view.launch(primaryStage, DefineClass.WIDTH, DefineClass.HEIGHT);
        initializeWallView(laby);
        loadImageViews();

    }
    public void refreshView(Labyrinth laby, IDeplacable player, List<IDeplacable> enemies){

        view.drawImageView(imageViewPlayer, game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY());
        view.drawImageView(imageViewDoor, game.getVertexDoor().getX(), game.getVertexDoor().getY());

        for(Vertex v: game.getLabyrinth().vertexSet()){
            List<IMapObject> listMapObject = v.getMapObjects();
            for (IMapObject o: listMapObject){
                ImageView imgView = findImageViewArrayMapObject(v.getX(), v.getY(), o);
                if (imgView != null)
                    view.drawImageView(imgView, v.getX(), v.getY());
            }

        }

    }
    private ImageView findImageViewArrayMapObject(int x, int y, IMapObject o){
        for (ImageView obj: arrayListImageViewItemsMap[x][y]){
            if (obj.getId() == o.getName())
                return obj;
        }
        return null;
    }

    public static ControllerView getInstance(){
        return instance;
    }

}
