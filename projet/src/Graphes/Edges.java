package Graphes;

import org.jgrapht.graph.DefaultEdge;

public class Edges extends DefaultEdge{

	public boolean equals(Edges o)
	{
		int j = ((Vertex)o.getSource()).compareTo(((Vertex)this.getSource()));
		int i = ((Vertex)o.getTarget()).compareTo(((Vertex)o.getTarget()));

		if (i == 0 && j == 0)
			return true;
		return false;
	}

}
