package Graphes;

import org.jgrapht.graph.SimpleGraph;
import Model.DefineClass;

public class Labyrinth<Vertex,Edges> extends SimpleGraph<Vertex,Edges> {

	public Labyrinth(Class<? extends Edges> edgeClass) {
		super(edgeClass);
		nbVertex = 0;
	}
	public boolean addVertex(Vertex v)
	{
		if (super.addVertex(v))
		{
			nbVertex ++;
			return true;
		}
		return false;
	}
	public void GeneratePerfectLabyrinth(Vertex v)
	{

	}

	public int nbVertex;



}
