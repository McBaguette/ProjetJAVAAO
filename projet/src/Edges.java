import org.jgrapht.graph.DefaultEdge;

public class Edges extends DefaultEdge implements Comparable<Edges>{

	public boolean equals(Edges o)
	{
		System.out.println("eeeie");
		int j = ((Vertex)o.getSource()).compareTo(((Vertex)this.getSource()));
		int i = ((Vertex)o.getTarget()).compareTo(((Vertex)o.getTarget()));

		if (i == 0 && j == 0)
			return true;
		return false;
	}
	@Override
	public int compareTo(Edges o) {
		System.out.println("eeeie");
		int j = ((Vertex)o.getSource()).compareTo(((Vertex)this.getSource()));
		int i = ((Vertex)o.getTarget()).compareTo(((Vertex)o.getTarget()));

		if (i == 0 && j == 0)
			return 0;
		return -1;
	}
}
