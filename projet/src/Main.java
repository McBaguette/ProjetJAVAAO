import graphe.Edges;
import graphe.Labyrinth;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Vertex;

public class Main extends Application{
    public static int p = 0;
    public static void main(String[] args) {
        Labyrinth<Vertex, Edges> G = new Labyrinth<>(Edges.class);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    @Override
    public void stop(){
        System.exit(0);
    }
}
