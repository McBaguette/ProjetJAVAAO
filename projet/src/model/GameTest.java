package model;

import graphe.Vertex;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import static junit.framework.Assert.fail;

import view.Images;

/**
 * Created by jakod on 04/12/2017.
 */
public class GameTest {
    @Test
    public void Test(){

        //to test the game generation
        int nbWhile = 100;

        for (int i = 0; i < nbWhile; i++){
            Game.getInstance().launch(false);
            List<Vertex> path = new LinkedList<>();
            path.add(Game.getInstance().getPlayer().getPosition());
            Game.getInstance().getLabyrinth().launchManhattan(Game.getInstance().getPlayer().getPosition(), Game.getInstance().getVertexDoor());
            Vertex actual = path.get(0);
            while (!actual.equals(Game.getInstance().getVertexDoor())){
                for (DefineClass.Directions dir : DefineClass.Directions.values()){
                    Vertex next = Game.getInstance().getLabyrinth().getNeighborVertex(actual, dir);
                    if (next != null && next.getNbr() == actual.getNbr() - 1){
                        path.add(next);
                        actual = next;
                        break;
                    }
                }
            }
            System.out.println("Start:");
            path.remove(0);

            while(path.size() > 0){
                Game.getInstance().getPlayer().setPosition(path.get(0));
                path.remove(0);
                for (IDeplacable enemy: Game.getInstance().getEnemies()){
                    Game.getInstance().getLabyrinth().launchManhattan(enemy.getPosition(), Game.getInstance().getPlayer().getPosition());
                    ((NPC)enemy).move(Game.getInstance().getLabyrinth());
                }
                for (IDeplacable enemy: Game.getInstance().getEnemies()){
                    if (enemy.getPosition().equals(Game.getInstance().getPlayer().getPosition()))
                        fail("Not suppose to be game over");
                }

            }


        }
    }
}
