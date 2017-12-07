package graphe;

import org.jgrapht.graph.DefaultEdge;

import model.DefineClass.Type;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge {
	private Type type;

	public Edge() {
		super();
		this.type = Type.CORRIDOR;
	}
	
	public Edge(Type type) {
		this();
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

	public boolean equals(Object obj) {
		if (!(obj instanceof Edge)) return false;
		Edge e = (Edge)obj;
		return this.getSource().equals(e.getSource()) && this.getTarget().equals(e.getTarget());
	}
	public String toDot(){
		return ((Vertex)this.getSource()).dotName() + " -- " +((Vertex)this.getTarget()).dotName();
	}

}
