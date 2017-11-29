package graphe;

import model.mapobject.IMapObject;
import model.mapobject.MapObject;

public class Vertex {

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		nbr = 0;
		mapObject = null;
	}

	private int x, y;
	private int nbr;
	private IMapObject mapObject;

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

	public IMapObject getMapObject(){
		return mapObject;
	}
	public void setMapObject(IMapObject o){
		mapObject = o;
	}

	public String dotName(){
		return x + " ; "+y;
	}
	public String toDot(){
		return dotName() + "[ label=\"" + dotName() + "\"]";
	}
}
