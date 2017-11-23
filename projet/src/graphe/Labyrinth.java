package graphe;

import model.DefineClass;
import org.jgrapht.graph.SimpleGraph;

public class Labyrinth<Vertex, Edges> extends SimpleGraph<Vertex, Edges> {
	public int nbVertex;
	
	public Labyrinth(Class<? extends Edges> edgeClass) {
		super(edgeClass);
		nbVertex = 0;
	}

	public boolean addVertex(Vertex v) {
		if (super.addVertex(v)) {
			nbVertex++;
			return true;
		}
		return false;
	}

	public void GeneratePerfectLabyrinth(Vertex v) {

	}

	public Vertex getNeighborVertex(Vertex v, DefineClass.Directions dir){
		//Emile si tu vois ça, c'est que je l'ai pas implémenté et si tu veux le faire c'est en fait la méthode getVertexByDir du prof.
		//TODO
		return null;
	}

}
