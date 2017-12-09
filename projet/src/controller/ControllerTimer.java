package controller;

import graphe.Labyrinth;
import graphe.Vertex;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DefineClass;
import model.Game;
import view.Images;
import view.View;

/**
 * Class ControllerTimer : this class launch the fonctions to start the view and the model and after that create a timer to manage the game.
 */

public class ControllerTimer{

    private static ControllerTimer instance = new ControllerTimer();
    private static int valueTimer = 50;
    private static int timeBegining = 3000;
    private int nbTick;
    private Game game;
    private ControllerView controllerView;
    private ControllerUser controllerUser;
    private Timeline timeline;

    private ControllerTimer (){
        game = Game.getInstance();
        controllerUser = ControllerUser.getInstance();
        controllerView = ControllerView.getInstance();
        nbTick = 0;
        timeline = new Timeline();
    }

    /**
     * Start the launch functions of the view and the model and launch the timer.
     * Also initialise a KeyEvent Handler for keyboardinput that called movePlayer method from Game class.
     */
    public void launch(Stage primaryStage) {
        game.launch(false);
        controllerView.launch(primaryStage, game.getLabyrinth());
        controllerView.refreshView();
        nbTick = 0;
        initTimer();

    }

    /**
     * Call at the begining of the game, to create the game timer and start it. Use the data member valueTimer to stare the tick of the timer.
     */
    public void initTimer(){
        final KeyFrame keyFrameForModel = new KeyFrame(Duration.millis(valueTimer), actionEvent -> handle(actionEvent));
        timeline.getKeyFrames().addAll(keyFrameForModel);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Call by the timer every valueTimer millisecond, use the direction method to move the player.
     * After that this function call manageGame() to get a return value of the state of the game, to now if the game is over or not : if the return value is 1 it call the restart method with true, false is the value is-1.
     * If not 1 or -1, the game continue. 
     * @param event
     */
    public void handle(ActionEvent event) {
        if (nbTick*valueTimer >= timeBegining )
        {
            game.movePlayer(controllerUser.getDirectionsPlayer());
            switch(game.manageGame()) {
            case 1:
                restart(true);
                break;
            case -1:
                restart(false);
            	break;
            default:
            	break;
            }
            controllerView.refreshView();
        }
        else
            nbTick ++;

    }
    
    /**
     * This function start a new game with a higher difficulty in function of the param, if the player had win the game the param is true, if not false. 
     * @param increaseDifficulty : if true the difficuly is increase, if not it keep the same difficulty.
     */
    
    private void restart(boolean increaseDifficulty){
        game.launch(true);
        controllerView.restart(game.getLabyrinth());
        timeline.playFromStart();
        nbTick = 0;
    }
    
    /**
     * Return the unique instance of this class.  
     */
    
    public static ControllerTimer getInstance() {
        return instance;
    }
}
