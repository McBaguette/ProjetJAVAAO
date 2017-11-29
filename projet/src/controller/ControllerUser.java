package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import view.View;

/**
 * Created by jakod on 29/11/2017.
 */
public class ControllerUser implements  IController {
    private static ControllerUser instance = new ControllerUser();
    public static ControllerUser getInstance(){
        return instance;
    }


    public void setOnAction(){
        View.getInstance().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("test keyboard events");
            }
        });
    }
}
