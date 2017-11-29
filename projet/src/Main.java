import controller.Controller;
import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static int p = 0;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.getInstance().launch(primaryStage);
    }
    @Override
    public void stop(){
        System.exit(0);
    }
}
