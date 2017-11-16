import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge implements Comparable<Edge>{
	public Edge()
	{
		super();
	}

	@Override
	public int compareTo(Edge o) {
		int j = ((Vertex)o.getSource()).compareTo(((Vertex)this.getSource()));
		int i = ((Vertex)o.getTarget()).compareTo(((Vertex)o.getTarget()));
		
		if (i == 0 && j == 0)
			return 0;
		return -1;
	}
}
