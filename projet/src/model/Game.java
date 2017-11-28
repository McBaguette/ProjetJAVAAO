package model;

import graphe.Edge;
import graphe.Labyrinth;
import graphe.Vertex;
import model.mapobject.Candy;
import model.mapobject.IMapObject;

import java.util.LinkedList;
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


        //place candies (random)
        int nbCandies = 0;
        while(nbCandies < DefineClass.NUMBER_CANDIES_ON_MAP) {
            int x = (int) (Math.random() * (DefineClass.SOUTH_BORDER+1));
            int y = (int) (Math.random() * (DefineClass.EAST_BORDER+1));
            Vertex v = labyrinth.getVertex(x,y);
            if (v.getMapObject() == null){
                v.setMapObject(Candy.getInstance());
                nbCandies ++;
            }
        }


        //place door (random)
        int x = (int) (Math.random() * (DefineClass.SOUTH_BORDER+1));
        int y = (int) (Math.random() * (DefineClass.EAST_BORDER+1));
        Vertex vertexDoor = labyrinth.getVertex(x,y);
        //vertexDoor.setObjeMap = DOOR;


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
        int nbMonstre = 2;
        int maxSizePath;
        int nbWhileMax = 100;
        List<Vertex> listPathPlayer;
        List<List<Vertex>> listPathEnemies;

        int nbWhile = 0;
        do
        {
            nbWhile ++;
            //we search the differents paths from enemies to the player
            maxSizePath = 0;
            listPathEnemies = new LinkedList<>();
            for (int i = 0; i < nbMonstre; i++) {
                listPathEnemies.add(new LinkedList<Vertex>());
                labyrinth.launchManhattan(enemies.get(i).getPosition(),player.getPosition());
                listPathEnemies.get(i).add(enemies.get(i).getPosition());
                findPath(labyrinth, listPathEnemies.get(i), player.getPosition());
                if (listPathEnemies.get(i).size() > maxSizePath)
                    maxSizePath = listPathEnemies.get(i).size();
            }

            //we launch Manhattan algorithm to find path from the player to the door
            labyrinth.launchManhattan(player.getPosition(), vertexDoor);

            listPathPlayer = new LinkedList<>();
            listPathPlayer.add(player.getPosition());
            for (int index = 0; index < maxSizePath; index ++){
                for(DefineClass.Directions dir : DefineClass.Directions.values()){
                    Vertex neighbor = labyrinth.getNeighborVertex(listPathPlayer.get(index), dir);
                    if (neighbor.getNbr() == listPathPlayer.get(index).getNbr() - 1){
                        //we check if the next position of an enemy is not the neighbor
                        boolean onEnemiesPaths = false;
                        for (List l : listPathEnemies){
                            if (index+1 < l.size() && l.get(index+1).equals(neighbor)){
                                onEnemiesPaths = true;
                                break;
                            }
                        }
                        //if not, we can add the vertex to the player's path
                        if (!onEnemiesPaths){
                            listPathPlayer.add(neighbor);
                        }
                    }
                }
            }
        }while (listPathPlayer.size() < maxSizePath && nbWhile < nbWhileMax);






    }
    private void findPath(Labyrinth g, List<Vertex> path, Vertex end){
        Vertex actual = path.get(0);
        while (!actual.equals(end)){
            for (DefineClass.Directions dir : DefineClass.Directions.values()){
                Vertex next = g.getNeighborVertex(actual, dir);
                if (next.getNbr() == actual.getNbr() - 1){
                    path.add(next);
                    actual = next;
                    break;
                }
            }
        }
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
