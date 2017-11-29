package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DefineClass;
import model.Game;
import view.View;



public class Controller implements IController {

    private static Controller INSTANCE = null;
    private Game game;
    private View view;
    private EventHandler<KeyEvent> handler;

    public Controller (){
        game = Game.getInstance();
        view = View.getInstance();
    }

    /**
     * Start the launch functions of the view and the model and launch the timer.
     * Also initialise a KeyEvent Handler to for keyboardinput that called movePlayer method from Game class.
     */
    public void launch(Stage primaryStage) {

        game.launch();
        view.launch(primaryStage, DefineClass.WIDTH, DefineClass.HEIGHT);
        view.drawWalls(Game.getInstance().getLabyrinth());

        this.runTimer();
        handler = KeyEvent -> {
            KeyCode keycode = KeyEvent.getCode();
            if(KeyEvent.getEventType() == KeyEvent.KEY_PRESSED){
                if (keycode == KeyCode.RIGHT)
                    game.movePlayer(DefineClass.Directions.EAST);
                if (keycode == KeyCode.LEFT)
                    game.movePlayer(DefineClass.Directions.WEST);
                if (keycode == KeyCode.UP)
                    game.movePlayer(DefineClass.Directions.NORTH);
                if (keycode == KeyCode.DOWN)
                    game.movePlayer(DefineClass.Directions.EAST);
            }
        };
    }

    /**
     *
     * @param primaryStage
     */
    public void start(Stage primaryStage){
        //view.getScene().addEventHandler(KeyEvent.ANY, handler);
    }


    /**
     Create a timeline and two keyframes at 16.7 seconds to update model and view (for 60 fps).
     */

    public void runTimer(){
        /*
        Timeline timeline = new Timeline();
        final KeyFrame keyFrameForView = new KeyFrame(Duration.millis(16.7), actionEvent -> view.update());
        final KeyFrame keyFrameForModel = new KeyFrame(Duration.millis(16.7), actionEvent -> game.manageGame());
        timeline.getKeyFrames().addAll(keyFrameForView, keyFrameForModel);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/
    }

    public static Controller getInstance(){
        if(INSTANCE == null)
            INSTANCE=new Controller();
        return INSTANCE;
    }


    @Override
    public void setOnAction() {

    }
}
