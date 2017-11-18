import java.util.Map;

import org.jgrapht.VertexFactory;
import org.jgrapht.graph.SimpleGraph;

public class Graph<Vertex,Edges> extends SimpleGraph<Vertex,Edges> {

	public Graph(Class<? extends Edges> edgeClass) {
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
	public int nbVertex;



}
