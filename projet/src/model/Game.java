package model;

import graphe.Edges;
import graphe.Labyrinth;

public class Game {
    private static Game instance = new Game();
    private Labyrinth labyrinth;

    public Game(){
        labyrinth = new Labyrinth(Edges.class);
    }

    public static Game getInstance(){
        return instance;
    }

    public Labyrinth getLabyrinth(){
        return labyrinth;
    }
}
