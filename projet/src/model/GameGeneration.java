package model;

import graphe.Labyrinth;
import graphe.Vertex;
import model.mapobject.Candy;

import java.util.LinkedList;
import java.util.List;

/**
 * class GameGeneration:
 * All methods here are static
 * In this class we generate a Labyrinth game
 */
public class GameGeneration {
    /**
     * Create a labyrinth game, place candies, place enemies, place player and create a game winnable
     * @param labyrinth
     * @param player
     * @param enemies
     * @param level
     * @return
     */
    public static Vertex generateLabyrithGame(Labyrinth labyrinth, IMovable player, List<IMovable> enemies, int level){
        int nbWhileMax = 1000;
        int nbWhile = 0;
        boolean found;
        Vertex vertexDoor;
        do
        {
            initEnemies(enemies, level);
            labyrinth.buildLabyrinth();
            labyrinth.createDoorsRandom(20/level, DefineClass.Type.OPENED_DOOR);
            labyrinth.createDoorsRandom(nbWhile/25,DefineClass.Type.OPENED_DOOR);



            //place candies (random)
            int nbCandies = 0;
            while(nbCandies < DefineClass.NUMBER_CANDIES_ON_MAP) {
                Vertex v = labyrinth.getRandomVertex();
                //we can have many objects on a vertex, but only one candy; and we will place other objects later.
                if (v.getMapObjects() != null && v.getMapObjects().size() == 0){
                    v.addMapObject(new Candy(UsefulFunctions.generateRandomNumber(0, DefineClass.NUMBER_CANDIES_TYPE-1)));
                    nbCandies ++;
                }
            }

            //place door (random)
            vertexDoor = labyrinth.getRandomVertex();
            //place player far from the door
            labyrinth.launchManhattan(labyrinth.getVertex(0,0), vertexDoor);

            int maxNbr = 0;
            graphe.Vertex vertexFarAway = null;
            for (Vertex v: labyrinth.vertexSet()) {
                if (v.getNbr() > maxNbr){
                    vertexFarAway = v;
                    maxNbr = v.getNbr();
                }
            }
            player.setPosition(vertexFarAway);


            nbWhile ++;
            //place randomly enemies
            int positionListEnemies = 0;
            for (IMovable enemy: enemies){
                //enemies can't be at the same position
                boolean ok = true;
                do{
                    ok = true;
                    enemy.setPosition(null);
                    enemy.setPosition(labyrinth.getRandomVertex());
                    for(int p = positionListEnemies; p > 0; p--){
                        if (enemies.get(p-1).getPosition().equals(enemy.getPosition())){
                            ok = false;
                            break;
                        }
                    }

                }while(!ok);
                positionListEnemies ++;
            }

            //we will simulate the game to see if is possible
            found = simulatePerfectGame(labyrinth, player, enemies, vertexDoor);
        }while (!found && nbWhile < nbWhileMax);
        if (!found){
            System.out.println("not found");
            return null;
        }
        //TODO
        //rajouter le fait d'enlever des bouts de murs, si c'est impossible de créer un jeu.
        return vertexDoor;
    }
    /**
     * To test if the game is possible or not, will simulates moves of the enemies and the player
     * @return to say if the game is possible or not
     */
    public static boolean simulatePerfectGame(Labyrinth labyrinth, IMovable player, List<IMovable> enemies, Vertex vertexDoor){
        List<Vertex> pathPlayer = new LinkedList<>();
        pathPlayer.add(player.getPosition());
        labyrinth.launchManhattan(pathPlayer.get(0), vertexDoor);
        findPath(labyrinth, pathPlayer, vertexDoor);
        if (pathPlayer.size() > 0)
            pathPlayer.remove(0);

        List<IMovable> copyEnemies = new LinkedList<>();
        for (IMovable e: enemies){
            copyEnemies.add(new NPC(e.getPosition()));
        }
        while(pathPlayer.size() > 0){
            Vertex positionPlayer = pathPlayer.get(0);
            pathPlayer.remove(0);

            for (IMovable enemy: copyEnemies){
                labyrinth.launchManhattan(enemy.getPosition(), positionPlayer);
                ((NPC)enemy).move(labyrinth);
            }
            for (IMovable enemy: copyEnemies){
                if (enemy.getPosition().equals(positionPlayer))
                    return false;
            }
        }

        return true;
    }
    /**
     * Initiliazed objects needed for the class.
     * @param level
     */
    public static void initEnemies(List<IMovable> enemies, int level){
        enemies.clear();
        int nbEnemies = level/2;
        for (int i = 0; i < nbEnemies && i < DefineClass.NUMBER_ENEMIES_MAX ;  i++)
            enemies.add(new NPC());
    }
    /**
     * Find the path from path.get(0) to to end
     * @param g the labyrinth
     * @param path will contains the result.
     * @param end the last point
     */
    public static void findPath(Labyrinth g, List<Vertex> path, Vertex end){
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
}
