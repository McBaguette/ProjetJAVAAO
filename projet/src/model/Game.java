package model;

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
    private Vertex vertexDoor;

    private int score;

    public Game(){
        labyrinth = new Labyrinth();
    }

    /**
     * Called by Controller, to start all the game.
     */
    public void launch(){
        score = 0;
        restart(0);
    }

    /**
     * Called by Controller every timer cycle
     * Manage all the game: move enemies, process collision (with enemies and objects on map)
     */
    public void manageGame(){
        moveEnemies();
        manageInteractionWithMap();
        if (manageInteractionWithEnemies() == -1)
            gameOver();
        System.out.println(score);
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
     * Call gameOver() of Controller
     */
    private void gameOver(){

    }

    /**
     * Call to create a new level, when the player went to the door
     * @param level which level you want
     */
    private void restart(int level){
        labyrinth = new Labyrinth();
        player = new PC();
        enemies = new LinkedList<IDeplacable>();
        for (int i = 0; i < level+1; i++)
            enemies.add(new NPC());

        generateLabyrinthGame(level);
    }

    /**
     * Call to create the labyrinth, place walls, objects, enemies, the player and the door
     * Checked if the game is winnable
     * @param level
     */
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
            //we can have many objects on a vertex, but only one candy; and we will place other objects later.
            if (v.getMapObjects() != null && v.getMapObjects().size() == 0){
                v.addMapObject(new Candy((int)(Math.random() * DefineClass.NUMBER_CANDIES_TYPE)));
                nbCandies ++;
            }
        }


        //place door (random)
        int x = (int) (Math.random() * (DefineClass.SOUTH_BORDER+1));
        int y = (int) (Math.random() * (DefineClass.EAST_BORDER+1));
        vertexDoor = labyrinth.getVertex(x,y);
        //vertexDoor.setObjeMap = DOOR;

        //place player far from the door
        labyrinth.launchManhattan(vertexDoor, labyrinth.getVertex(0,0));

        int maxNbr = 0;
        graphe.Vertex vertexFarAway = null;
        for (Vertex v: labyrinth.vertexSet()) {
            if (v.getNbr() > maxNbr){
                vertexFarAway = v;
                maxNbr = v.getNbr();
            }
        }
        player.setPosition(vertexFarAway);

        //place enemies en vérifiant que les enemis ne croiseront pas nécessairement le joueur
        int maxSizePath = 0;
        int nbWhileMax = 100;
        List<Vertex> listPathPlayer;
        List<List<Vertex>> listPathEnemies;
        int nbWhile = 0;
        boolean found = true;
        do
        {
            if (nbWhile%10 == 0)
                labyrinth.openDoorRandom();
            nbWhile ++;
            //place randomly enemies
            int positionListEnemies = 0;
            for (IDeplacable enemy: enemies){
                //enemies can't be at the same position
                boolean ok = true;
                do{
                    enemy.setPosition(null);
                    int coordX = (int) (Math.random() * (DefineClass.SOUTH_BORDER+1));
                    int coordY = (int) (Math.random() * (DefineClass.EAST_BORDER+1));
                    enemy.setPosition(labyrinth.getVertex(coordX, coordY));
                    for(int p = positionListEnemies; p != 0; p--){
                        if (enemies.get(p).getPosition().equals(enemy.getPosition())){
                            ok = false;
                            break;
                        }
                    }


                }while(!ok);
                positionListEnemies ++;
            }

            //we search the differents paths from enemies to the player
            maxSizePath = 0;
            listPathEnemies = new LinkedList<>();
            for (int i = 0; i < enemies.size(); i++) {
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
            //on trouve le chemin joueur->porte:

            //on le compare avec le chemin des enemies:
            int index = 0;
            while (!listPathPlayer.get(index).equals(vertexDoor)) {
                for (DefineClass.Directions dir: DefineClass.Directions.values()){
                    Vertex neighbor = labyrinth.getNeighborVertex(listPathPlayer.get(index), dir);
                    if (neighbor != null && neighbor.getNbr() == listPathPlayer.get(index).getNbr() -1){
                        listPathPlayer.add(neighbor);
                        index ++;
                    }
                }
            }

            index = 0;
            found = true;
            for (Vertex v: listPathPlayer){
                for (List<Vertex> listEnemie: listPathEnemies){
                    for (Vertex vEnemie:listEnemie){
                        if (!listPathPlayer.equals(vEnemie) && vEnemie.equals(v)) {
                            found = false;
                            break;
                        }
                    }
                    if (!found)
                        break;
                }
                if (!found)
                    break;
            }

        }while (!found && nbWhile < nbWhileMax);
        //TODO
        //rajouter le fait d'enlever des bouts de murs, si c'est impossible de créer un jeu.
    }

    /**
     * Find the path from path.get(0) to to end
     * @param g the labyrinth
     * @param path will contains the result.
     * @param end the last point
     */
    private void findPath(Labyrinth g, List<Vertex> path, Vertex end){
        Vertex actual = path.get(0);
        while (!actual.equals(end)){
            for (DefineClass.Directions dir : DefineClass.Directions.values()){
                Vertex next = g.getNeighborVertex(actual, dir);
                if (next != null && next.getNbr() == actual.getNbr() - 1){
                    path.add(next);
                    actual = next;
                    break;
                }
            }
        }
    }
    private int manageInteractionWithMap(){
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
