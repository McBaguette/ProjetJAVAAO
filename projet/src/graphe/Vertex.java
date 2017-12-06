package graphe;

import model.DefineClass;
import model.DefineClass.Directions;
import model.mapobject.IMapObject;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
	private int x, y;
	private int nbr;
	private List<IMapObject> mapObjects;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		nbr = 0;
		mapObjects = new LinkedList<>();
	}

	public Vertex(Vertex v, Directions dir) {
		this(v.getX(), v.getY());
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
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vertex)) return false;
		Vertex v = (Vertex) obj;
		return x == v.getX() && y == v.getY();
	}
	
	@Override
	public int hashCode() {
		return (Integer.MAX_VALUE >> 1)*x + y;
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
	public void setNbr(int n){
		nbr = n;
	}

	public List<IMapObject> getMapObjects(){
		return mapObjects;
	}
	public void removeMapObject(IMapObject o){
		mapObjects.remove(o);

	}
	public void addMapObject(IMapObject o){
		mapObjects.add(o);
	}

	public String dotName(){
		return "V"+x + "t"+y + "nbr"+getNbr();
	}
	public String toDot(){
		return dotName() + "[ label=\"" + dotName() + "\"]";
	}

	public boolean inBorders() {
		return x>=0 && y >= 0 && x <= DefineClass.SOUTH_BORDER && y <= DefineClass.EAST_BORDER;
	}
}
