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



public class ControllerTimer{

    private static ControllerTimer instance = new ControllerTimer();
    private static int valueTimer = 500;
    private static int timeBegining = 3000;
    private int nbTick;
    private Game game;
    private ControllerView controllerView;
    private ControllerUser controllerUser;

    private ControllerTimer (){
        game = Game.getInstance();
        controllerUser = ControllerUser.getInstance();
        controllerView = ControllerView.getInstance();
        nbTick = 0;
    }

    /**
     * Start the launch functions of the view and the model and launch the timer.
     * Also initialise a KeyEvent Handler to for keyboardinput that called movePlayer method from Game class.
     */
    public void launch(Stage primaryStage) {
        game.launch();
        controllerView.launch(primaryStage, game.getLabyrinth());
        controllerView.refreshView(game.getLabyrinth(), game.getPlayer(), game.getEnemies());
        nbTick = 0;
        initTimer();

    }

    /**
     * Call at the begining of the game, to create the game timer.
     */
    public void initTimer(){
        Timeline timeline = new Timeline();
        final KeyFrame keyFrameForModel = new KeyFrame(Duration.millis(valueTimer), actionEvent -> handle(actionEvent));
        timeline.getKeyFrames().addAll(keyFrameForModel);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Call by the timer every valueTimer millisecond
     * @param event
     */
    public void handle(ActionEvent event) {
        if (nbTick*valueTimer >= timeBegining )
        {
            game.movePlayer(controllerUser.getDirectionsPlayer());
            switch(game.manageGame()) {
            case 1:
            case -1:
            	initTimer();
            	break;
            default:
            	break;
            }
            controllerView.refreshView(game.getLabyrinth(), game.getPlayer(), game.getEnemies());
        }
        else
            nbTick ++;

    }

    public static ControllerTimer getInstance() {
        return instance;
    }
}
