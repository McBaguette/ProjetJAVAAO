package model;

import graphe.Labyrinth;
import graphe.Vertex;
import model.mapobject.Candy;
import model.mapobject.IMapObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class Game {
    private static Game instance = new Game();
    private Labyrinth labyrinth;

    private IMovable player;
    private List<IMovable> enemies;
    private Vertex vertexDoor;

    private int score, level, scoreForTheLevel;
    private Game(){
        labyrinth = new Labyrinth();
        player = new PC();
        enemies = new LinkedList<>();
        vertexDoor = new Vertex(0,0);
        score = 0;
        level = 2;
    }

    /**
     * Called by ControllerTimer, to start a level.
     * @param increaseDifficulty to say if the game has to be more difficult
     */
    public void launch(boolean increaseDifficulty){
        if (increaseDifficulty)
            level ++;

        launchNewLevel(level);
    }

    /**
     * Called by Controller every timer cycle
     * Manage all the game: move enemies, process collision (with enemies and objects on map)
     */
    public int manageGame(){
        moveEnemies();
        if (manageInteractionWithMap() == 1){
            score += scoreForTheLevel;
            System.out.println("Victory !");
            printScore();
            return 1;
        }
        if (manageInteractionWithEnemies() == -1){
            System.out.println("Defeat !");
            printScore();
            return -1;
        }
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
     * Call to create a new level, when the player went to the door
     * @param level which level you want
     */
    private void launchNewLevel(int level){
        scoreForTheLevel = 0;
        vertexDoor = GameGeneration.generateLabyrithGame(labyrinth, player, enemies, level);
        if (vertexDoor == null){
            System.out.println("Impossible to create the game");
            exit(0);
        }
    }


    /**
     * Here we check if the player is on the door (so the end of the level)
     * And check if the player is on a ObjectMap, call doAction associate to the ObjectMap and increase the score
     * @return 1 if the player is on the door (so if the player win) and 0 if no.
     */
    private int manageInteractionWithMap(){
        for (IMapObject o: player.getPosition().getMapObjects()) {
            o.doAction();
            scoreForTheLevel += o.getScore();
        }
        if (player.getPosition().equals(vertexDoor))
            return 1;

        return 0;
    }

    /**
     * Check if the player is on an enemy (so if the player lost)
     * @return -1 if the player lost, 0 if not
     */
    private int manageInteractionWithEnemies(){
        for (IMovable enemie: enemies){
            if (player.getPosition().equals((enemie).getPosition()))
                return -1;      //Game Over
        }
        return 0;
    }

    /**
     * Moves enemies, launchn manhattan between enemy and player, and call enemy.move()
     */
    private void moveEnemies(){
        for(IMovable e:enemies){
            labyrinth.launchManhattan(e.getPosition(), player.getPosition());
            ((NPC)e).move(labyrinth);
        }
    }

    /**
     * Print on console the score
     */
    public void printScore(){
        System.out.println("Your score is "+getScore());
    }

    public static Game getInstance(){
        return instance;
    }

    public Labyrinth getLabyrinth(){
        return labyrinth;
    }
    public IMovable getPlayer(){
        return player;
    }
    public List<IMovable> getEnemies(){
        return enemies;
    }
    public Vertex getVertexDoor(){return vertexDoor;}
    public int getScore(){return score;}
}
