package graphe;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge implements Comparable<Edge> {

	public enum Type {
		OPENED_DOOR, CLOSED_DOOR, CORRIDOR;
	};

	private Type type;

	public Edge(Type type) {
		super();
		this.type = type;
	}

	public Vertex getSource() {
		return (Vertex) super.getSource();
	}

	public Vertex getTarget() {
		return (Vertex) super.getTarget();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return true if the edge is traversable
	 *
	 * @deprecated Ne devrais plus être utilisé, il faudrai utiliser les méthodes de
	 *             labyrinth à la place
	 */
	public boolean isTraversable() {
		return (type == Type.CORRIDOR || type == Type.OPENED_DOOR);
	}

	@Override
	public int compareTo(Edge o) {
		int source = this.getSource().compareTo((o).getSource());
		if (source != 0)
			return source;
		else {
			return this.getTarget().compareTo((o).getTarget());
		}
	}

}
