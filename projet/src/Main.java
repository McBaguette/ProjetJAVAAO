import controller.Controller;
import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static int p = 0;
    public static void main(String[] args) {
        Labyrinth G = new Labyrinth();
        Controller controller=Controller.getInstance();
        controller.launch();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller c=Controller.getInstance();
        //c.start(primaryStage) ;
    }
    @Override
    public void stop(){
        System.exit(0);
    }
}
