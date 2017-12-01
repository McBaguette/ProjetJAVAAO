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
    private static int valueTimer = 250;
    private Game game;
    private ControllerView controllerView;
    private ControllerUser controllerUser;

    public ControllerTimer (){
        game = Game.getInstance();
        controllerUser = ControllerUser.getInstance();
        controllerView = ControllerView.getInstance();
    }

    /**
     * Start the launch functions of the view and the model and launch the timer.
     * Also initialise a KeyEvent Handler to for keyboardinput that called movePlayer method from Game class.
     */
    public void launch(Stage primaryStage) {
        game.launch();
        controllerView.launch(primaryStage, game.getLabyrinth());
        controllerView.refreshView(game.getLabyrinth(), game.getPlayer(), game.getEnemies());
        timer();

    }
    public void timer(){
        Timeline timeline = new Timeline();
        final KeyFrame keyFrameForModel = new KeyFrame(Duration.millis(valueTimer), actionEvent -> handle(actionEvent));
        timeline.getKeyFrames().addAll(keyFrameForModel);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     *
     * @param event
     */
    public void handle(ActionEvent event) {
        game.movePlayer(controllerUser.getDirectionsPlayer());
        game.manageGame();
        controllerView.refreshView(game.getLabyrinth(), game.getPlayer(), game.getEnemies());
    }

    public static ControllerTimer getInstance() {
        return instance;
    }
}
