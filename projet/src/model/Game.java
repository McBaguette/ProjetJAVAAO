package model;

import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;

import java.util.List;

public class Game {
    private static Game instance = new Game();
    private Labyrinth labyrinth;

    private IDeplacable player;
    private List<IDeplacable> enemies;

    private int score;

    public Game(){
        labyrinth = new Labyrinth();
    }

    /**
     * Called by Controller, to start all the game.
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

    /**
     * Call gameOver() of Controller
     */
    private void gameOver(){

    }
    private void restart(int level){
        score = 0;
    }
    private void generateLabyrinthGame(int level){
        //call Labyrinth.buildLabyrinth(nbArête)
        int numEdgesPerfectLabyrinth = DefineClass.HEIGHT*DefineClass.WIDTH*4 - (2*(2*DefineClass.HEIGHT + 2*DefineClass.WIDTH));
        labyrinth.buildLabyrinth(numEdgesPerfectLabyrinth - level*150);

        //place door (random)
        int x = (int) (Math.random() * (DefineClass.SOUTH_BORDER+1));
        int y = (int) (Math.random() * (DefineClass.EAST_BORDER+1));

        Vertex vertexDoor = labyrinth.getVertex(x,y);
        //vertexDoor.getVertex(x,y).setObjetMap = DOOR;

        //place candies (random)
        //TODO


        //place player far from the door
        labyrinth.launchManhattan(vertexDoor, vertexDoor);
        int maxNbr = 0;
        graphe.Vertex vertexFarAway = null;
        for (Object v: labyrinth.vertexSet()) {
            if (((graphe.Vertex)v).getNbr() > maxNbr){
                vertexFarAway = ((graphe.Vertex)v);
                maxNbr = ((graphe.Vertex)v).getNbr();
            }
        }
        player.setPosition(vertexFarAway);


        //place enemies en vérifiant que les enemis ne croiseront pas nécessairement le joueur


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
