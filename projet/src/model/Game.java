package model;

import graphe.Edges;
import graphe.Labyrinth;

import java.util.List;

public class Game {
    private static Game instance = new Game();
    private Labyrinth labyrinth;

    private IDeplacable player;
    private List<IDeplacable> enemies;

    private int score;

    public Game(){
        labyrinth = new Labyrinth(Edges.class);
    }

    /**
     * Called by main, to start all the game.
     */
    public void launch(){
        restart(0);
    }

    /**
     * Called by Controller every timer cycle
     * Manage all the game: move enemies, process collision (with enemies and objects on map)
     */
    public void manageGame(){

    }

    /**
     * Called by Controller when there is a keyboard event.
     * @param dir directions given by keyboard Event from Controller
     */
    public void movePlayer(DefineClass.Directions dir){

    }

    private void gameOver(){

    }
    private void restart(int level){
        score = 0;
    }
    private void generateLabyrinth(int level){

    }
    private void manageInteractionWithMap(){

    }
    private void manageInteractionWithEnemies(){

    }
    private void moveEnemies(){

    }

    public static Game getInstance(){
        return instance;
    }

    public Labyrinth getLabyrinth(){
        return labyrinth;
    }
    public IDeplacable getPlayer(){
        return player;
    }
    public List<IDeplacable> getEnemies(){
        return enemies;
    }
}
