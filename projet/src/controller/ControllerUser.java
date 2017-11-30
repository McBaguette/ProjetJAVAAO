package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.DefineClass;
import model.Game;
import view.View;

import java.security.Key;

/**
 * Created by jakod on 29/11/2017.
 */
public class ControllerUser implements EventHandler<KeyEvent> {
    private static ControllerUser instance = new ControllerUser();
    private Game game;
    private View view;
    private Controller controller;
    private DefineClass.Directions valdir ;
    public static ControllerUser getInstance(){
        return instance;
    }

    private ControllerUser(){
        game = Game.getInstance();
        view = View.getInstance();
        controller = Controller.getInstance();
    }

    public void timer(){
        Timeline timeline = new Timeline();
        //final KeyFrame keyFrameForView = new KeyFrame(Duration.millis(500), actionEvent -> view.draw());
        final KeyFrame keyFrameForModel = new KeyFrame(Duration.millis(500), actionEvent -> game.movePlayer(valdir));
        timeline.getKeyFrames().addAll(keyFrameForModel);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode keycode = event.getCode();
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (keycode == KeyCode.RIGHT)
                valdir = DefineClass.Directions.EAST;
            if (keycode == KeyCode.LEFT)
                valdir = DefineClass.Directions.WEST;
            if (keycode == KeyCode.UP)
                valdir = DefineClass.Directions.NORTH;
            if (keycode == KeyCode.DOWN)
                valdir = DefineClass.Directions.SOUTH;
        }
        if(event.getEventType() == KeyEvent.KEY_RELEASED) {
            valdir = null;
        }
    }
}
