package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.DefineClass;
import model.Game;
import view.View;

/**
 * Created by jakod on 29/11/2017.
 */
public class ControllerUser implements EventHandler<KeyEvent>{
    private static ControllerUser instance = new ControllerUser();
    private KeyCode keycode;

    public static ControllerUser getInstance(){
        return instance;
    }

    private ControllerUser(){
    }



    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED)
            keycode = event.getCode();


        if(event.getEventType() == KeyEvent.KEY_RELEASED)
            keycode = null;

    }
    public DefineClass.Directions getDirectionsPlayer(){
        if (keycode == KeyCode.RIGHT)
            return DefineClass.Directions.EAST;
        if (keycode == KeyCode.LEFT)
            return DefineClass.Directions.WEST;
        if (keycode == KeyCode.UP)
            return DefineClass.Directions.NORTH;
        if (keycode == KeyCode.DOWN)
            return DefineClass.Directions.SOUTH;
        return null;

    }
    //aura pour but de renvoyer échap par exemple pour la pause
    public int getOthersEvent(){
        return 0;
    }
}
