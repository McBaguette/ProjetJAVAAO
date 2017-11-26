package graphe;

public class Vertex {

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		nbr = 0;
	}

	private int x, y;
	private int nbr;

	public boolean equals(Object obj) {
		if (!(obj instanceof Vertex)) return false;
		Vertex v = (Vertex) obj;
		return x == v.getX() && y == v.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNbr() {
		return nbr;
	}
}
