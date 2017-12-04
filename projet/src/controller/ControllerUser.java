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


    /**
     * Handler created in the View, called when there is a keyboard event.
     * @param event from the keyboard
     */
    @Override
    public void handle(KeyEvent event) {
        //to do movement as Pacman, we don't look released keys
        if (event.getEventType() == KeyEvent.KEY_PRESSED)
            keycode = event.getCode();
    }

    /**
     * get the key pressing, and in function return the direction.
     * @return the direction where the player want to go.
     */
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
    //aura pour but de renvoyer Echap par exemple pour la pause
    public int getOthersEvent(){
        return 0;
    }
}
