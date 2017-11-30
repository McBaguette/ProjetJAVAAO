package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import view.View;

import java.security.Key;

/**
 * Created by jakod on 29/11/2017.
 */
public class ControllerUser implements EventHandler<KeyEvent> {
    private static ControllerUser instance = new ControllerUser();
    public static ControllerUser getInstance(){
        return instance;
    }

    private ControllerUser(){

    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println("test clavier");
    }
}
