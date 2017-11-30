package controller;

import graphe.Labyrinth;
import graphe.Vertex;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DefineClass;
import model.Game;
import view.Images;
import view.View;



public class Controller implements EventHandler<ActionEvent>{

    private static Controller instance = new Controller();
    private Game game;
    private View view;
    private ControllerUser controllerUser;
    private EventHandler<KeyEvent> handler;

    public Controller (){
        game = Game.getInstance();
        view = View.getInstance();
        controllerUser = ControllerUser.getInstance();
    }

    /**
     * Start the launch functions of the view and the model and launch the timer.
     * Also initialise a KeyEvent Handler to for keyboardinput that called movePlayer method from Game class.
     */
    public void launch(Stage primaryStage) {

        game.launch();
        view.launch(primaryStage, DefineClass.WIDTH, DefineClass.HEIGHT);
        initializeWallView();
        refreshView();
        controllerUser.timer();

    }
    private void initializeWallView(){
        Labyrinth lab = game.getLabyrinth();
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
    public void refreshView(){

        view.drawImage(Images.imagePlayer, game.getPlayer().getPosition().getX(), game.getPlayer().getPosition().getY());
    }

    public static Controller getInstance(){
        return instance;
    }


    @Override
    public void handle(ActionEvent event) {

    }
}
