package graphe;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge implements Comparable<Edge> {

	/*Selon notre UML, c'est dans DefineClass le Type, comme ça on a tout au même endroit, et ça évite d'avoir du static avec du non static*/
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
	 * @deprecated Ne devrais plus �tre utilis�, il faudrai utiliser les m�thodes de
	 *             labyrinth � la place
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
