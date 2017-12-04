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
    private boolean actionDone = true; //To be sure that at least one action is executed by a pressed key

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
        if (actionDone && event.getEventType() == KeyEvent.KEY_PRESSED) {
            keycode = event.getCode();
        	actionDone = false;
        }
        if(actionDone && event.getEventType() == KeyEvent.KEY_RELEASED) {
            keycode = null;
        }
    }

    /**
     * get the key pressing, and in function return the direction.
     * @return the direction where the player want to go.
     */
    public DefineClass.Directions getDirectionsPlayer(){
        if (keycode == KeyCode.RIGHT) {
        	actionDone = true;
        	keycode = null;
        	return DefineClass.Directions.EAST;
        }   
        if (keycode == KeyCode.LEFT) {
        	actionDone = true;
        	keycode = null;
            return DefineClass.Directions.WEST;
        }
        if (keycode == KeyCode.UP) {
        	actionDone = true;
        	keycode = null;
            return DefineClass.Directions.NORTH;
        }
        if (keycode == KeyCode.DOWN) {
        	actionDone = true;
        	keycode = null;
            return DefineClass.Directions.SOUTH;
        }
        return null;

    }
    //aura pour but de renvoyer Echap par exemple pour la pause
    public int getOthersEvent(){
        return 0;
    }
}
