package graphe;

import model.DefineClass;
import model.DefineClass.Directions;
import model.mapobject.IMapObject;
import model.mapobject.MapObject;

public class Vertex {
	private int x, y;
	private int nbr;
	private IMapObject mapObject;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		nbr = 0;
		mapObject = null;
	}
	
	public Vertex(Vertex v, Directions dir) {
		this.x = v.getX();
		this.y = v.getY();
		switch (dir) {
		case NORTH:
			--y;
			break;
		case SOUTH:
			++y;
			break;
		case EAST:
			++x;
			break;
		case WEST:
			--x;
			break;
		}
		nbr = 0;
		mapObject = null;
	}

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
		return x + "_"+y;
	}
	public String toDot(){
		return dotName() + "[ label=\"" + dotName() + "\"]";
	}

	public boolean inBorders() {
		return x>=0 && y >= 0 && x <= DefineClass.SOUTH_BORDER && y <= DefineClass.EAST_BORDER;
	}
}
