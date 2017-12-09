package model;

import graphe.Vertex;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import static junit.framework.Assert.fail;

import view.Images;

/**
 * Test if the game is possible
 */
public class GameTest {
    @Test
    public void Test(){

        //to test the game generation
        int nbWhile = 10;

        for (int i = 0; i < nbWhile; i++){
            Game.getInstance().launch(true);
            //search the player's path
            List<Vertex> pathPlayer = new LinkedList<>();
            pathPlayer.add(Game.getInstance().getPlayer().getPosition());
            Game.getInstance().getLabyrinth().launchManhattan(Game.getInstance().getPlayer().getPosition(), Game.getInstance().getVertexDoor());
            Vertex actual = pathPlayer.get(0);
            while (!actual.equals(Game.getInstance().getVertexDoor())){
                for (DefineClass.Directions dir : DefineClass.Directions.values()){
                    Vertex next = Game.getInstance().getLabyrinth().getNeighborVertex(actual, dir);
                    if (next != null && next.getNbr() == actual.getNbr() - 1){
                        pathPlayer.add(next);
                        actual = next;
                        break;
                    }
                }
            }
            System.out.println("Start:");
            pathPlayer.remove(0);

            while(pathPlayer.size() > 0){
                Game.getInstance().getPlayer().setPosition(pathPlayer.get(0));
                pathPlayer.remove(0);
                for (IMovable enemy: Game.getInstance().getEnemies()){
                    Game.getInstance().getLabyrinth().launchManhattan(enemy.getPosition(), Game.getInstance().getPlayer().getPosition());
                    ((NPC)enemy).move(Game.getInstance().getLabyrinth());
                }
                for (IMovable enemy: Game.getInstance().getEnemies()){
                    if (enemy.getPosition().equals(Game.getInstance().getPlayer().getPosition()))
                        fail("Not suppose to be game over");
                }

            }


        }
    }
}
