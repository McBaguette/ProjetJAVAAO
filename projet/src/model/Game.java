package model;

import graphe.Labyrinth;
import graphe.Vertex;
import model.mapobject.Candy;
import model.mapobject.IMapObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Game {
    private static Game instance = new Game();
    private Labyrinth labyrinth;

    private IDeplacable player;
    private List<IDeplacable> enemies;
    private Vertex vertexDoor;

    private int score, level;
    private Game(){
        labyrinth = new Labyrinth();
        player = new PC();
        enemies = new LinkedList<>();
        vertexDoor = new Vertex(0,0);
        score = 0;
        level = 1;
    }

    /**
     * Called by Controller, to start a level.
     */
    public void launch(boolean increaseDifficulty){
        if (increaseDifficulty)
            level ++;
        try {
            launchNewLevel(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by Controller every timer cycle
     * Manage all the game: move enemies, process collision (with enemies and objects on map)
     */
    public int manageGame(){
        moveEnemies();
        if (manageInteractionWithMap() == 1){
            System.out.println("Victory !");
            return 1;
        }

        if (manageInteractionWithEnemies() == -1){
            System.out.println("Game over !");
            return -1;
        }
        //System.out.println(score);
        return 0;

    }

    /**
     * Called by Controller when there is a keyboard event.
     * @param dir directions given by keyboard Event from Controller
     */
    public void movePlayer(DefineClass.Directions dir){
        if (dir != null)
            ((PC)player).move(dir);
    }

    /**
     * I don't know if it will be use
     */
    private void gameOver(){

    }

    /**
     * Call to create a new level, when the player went to the door
     * @param level which level you want
     */
    private void launchNewLevel(int level) throws Exception{
        vertexDoor = GameGeneration.generateLabyrithGame(labyrinth, player, enemies, level);
        if (vertexDoor == null){
            throw new Exception("Impossible to create a game");
        }
    }





    private int manageInteractionWithMap(){
        if (player.getPosition().equals(vertexDoor))
            return 1;
        for (IMapObject o: player.getPosition().getMapObjects()){
            o.doAction();
            score += o.getScore();
        }
        return 0;
    }
    private int manageInteractionWithEnemies(){
        for (IDeplacable enemie: enemies){
            if (player.getPosition().equals((enemie).getPosition()))
                return -1;      //Game Over
        }
        return 0;
    }
    private void moveEnemies(){
        for(IDeplacable e:enemies){
            labyrinth.launchManhattan(e.getPosition(), player.getPosition());
            ((NPC)e).move(labyrinth);
        }
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
    public Vertex getVertexDoor(){return vertexDoor;}
}
