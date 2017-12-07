package graphe;

import model.DefineClass;
import model.DefineClass.Directions;
import model.DefineClass.Type;
import model.UsefulFunctions;
import model.mapobject.Switch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import org.jgrapht.graph.SimpleGraph;

@SuppressWarnings("serial")
public class Labyrinth extends SimpleGraph<Vertex, Edge> {

	public Labyrinth() {
		super(Edge.class);
	}

	public graphe.Vertex getVertex(int x, int y) {
		Set<Vertex> setVertex = this.vertexSet();
		for (Vertex v : setVertex) {
			if (v.getX() == x && v.getY() == y)
				return v;
		}
		return null;
	}

	public void buildLabyrinth() {

		if (this.vertexSet() != null && this.vertexSet().size() != 0){
			Object[] setv = this.vertexSet().toArray();
			for (int i = 0; i < setv.length; i++){
				this.removeVertex((Vertex) setv[i]);
			}
		}
		if (this.edgeSet() != null && this.edgeSet().size() != 0){
			Object[] setE = this.edgeSet().toArray();
			for (int i = 0; i < setE.length; i++)
				this.removeEdge((Edge)setE[i]);
		}


		Vertex v = new Vertex(0, 0);
		this.addVertex(v);
		GeneratePerfectLabyrinth(v);
	}

	private void GeneratePerfectLabyrinth(Vertex vertex) {
		// une liste aleatoire des 4 directions
		Random random = new Random();
		Vector<Directions> v = new Vector<DefineClass.Directions>();
		for (int i = 0; i < 4; ++i)
			v.add(Directions.values()[i]);
		Directions directions[] = new Directions[4];
		for (int i = 0; i < directions.length; ++i) {
			int index = random.nextInt(v.size());
			directions[i] = v.get(index);
			v.remove(index);
		}

		// pour chacune de ces directions, on avance en profondeur d'abord
		for (int i = 0; i < 4; ++i) {
			Directions dir = directions[i];
			Vertex v2 = new Vertex(vertex, dir);
			if (v2.inBorders() && !this.containsVertex(v2)) {
				this.addVertex(v2);
                try{
                    this.addEdge(vertex, v2);
                }
                catch(Exception e){
                    System.out.println(this.containsVertex(vertex) + " et "+this.containsVertex(v2));
                    System.out.println("erreur edge");
                    e.printStackTrace();
                }
				GeneratePerfectLabyrinth(v2);
			}
		}
	}

	public void createDoorsRandom(int number, Type type){
	    for (int i = 0; i < number; i++){
	        createDoorRandom(type);
        }
    }

	/**
	 * Try to create a new doors
	 */
	private void createDoorRandom(Type type) {
		Random random = new Random();
		// On essaie 1000 fois, apres quoi on renonce
		for (int i = 1; i <= 1000; ++i) {
			// On choisi un sommet au hasard
			Vertex vertex = this.getVertex(random.nextInt(DefineClass.WIDTH-1), random.nextInt(DefineClass.HEIGHT-1));
			if (vertex != null) {
				// On choisi une direction au hasard (on devrait prendre seulement
				// celles qui correspondent a desmurs...)
				Directions dir = Directions.values()[random.nextInt(Directions.values().length)];
				if (isWall(vertex, dir)) {
					Vertex vertex2 = getVertexByDir(vertex, dir);
					if (vertex2 != null) {
						Edge newEdge = new Edge(type);
						addEdge(vertex, vertex2, newEdge);
						addSwitch(newEdge);
						return;
					}
				}
			}
		}
	}

	/**
	 * @param Edge
	 *            of type OPENED_DOOR or CLOSED_DOOR
	 * 
	 *            Create and add a swtich in the labyrinth which is linked with the
	 *            door
	 */
	public void addSwitch(Edge door) {
		Random random = new Random();
		Vertex v = this.getVertex(random.nextInt(DefineClass.WIDTH), random.nextInt(DefineClass.HEIGHT));
		while(v == null || v.getMapObjects().size() > 0) {
			v = this.getVertex(random.nextInt(DefineClass.WIDTH), random.nextInt(DefineClass.HEIGHT));
		}
		v.addMapObject(new Switch(door));
	}

	/**
	 * @param v
	 *            Vertex
	 * @param dir
	 *            Direction
	 * @return vertex in the direction dir from v.
	 */
	public Vertex getVertexByDir(Vertex v, DefineClass.Directions dir) {
		int targetX = v.getX(), targetY = v.getY();
		switch (dir) {
		case NORTH:
			--targetY;
			break;
		case EAST:
			++targetX;
			break;
		case SOUTH:
			++targetY;
			break;
		case WEST:
			--targetX;
			break;
		default:
			return null;
		}

		return getVertex(targetX, targetY);
	}

	/**
	 * @param v
	 *            Vertex
	 * @param dir
	 *            Direction
	 * @return vertex in the direction dir from v if an edge link them.
	 */
	public Vertex getNeighborVertex(Vertex v, DefineClass.Directions dir) {
		int targetX = v.getX(), targetY = v.getY();
		switch (dir) {
		case NORTH:
			--targetY;
			break;
		case EAST:
			++targetX;
			break;
		case SOUTH:
			++targetY;
			break;
		case WEST:
			--targetX;
			break;
		default:
			return null;
		}

		Set<Edge> neighbors = this.edgesOf(v);
		for (Edge e : neighbors) { // Ne devrais pas faire plus de 4 passages, sauf erreur de conception du
									// labyrinthe
			if (e.getTarget().getX() == targetX && e.getTarget().getY() == targetY)
				return e.getTarget();
			if (e.getSource().getX() == targetX && e.getSource().getY() == targetY)
				return e.getSource();

		}
		return null;
	}

	/*
	 * public boolean containVertex(Vertex v) { Set VertexSet = this.vertexSet();
	 * return }
	 */

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v is a wall, else false.
	 */
	public boolean isWall(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);

		if (u == null) {
			return true;
		}
		return getEdge(v, u).getType() == Type.WALL;
	}

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v is a door else false.
	 */
	public boolean isDoor(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);
		Edge edge = this.getEdge(u, v);
		return (edge != null && (edge.getType() == Type.OPENED_DOOR || edge.getType() == Type.CLOSED_DOOR));
	}

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v is a closed door else
	 *         false.
	 */
	public boolean isDoorClosed(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);
		Edge edge = this.getEdge(u, v);
		return (edge != null && edge.getType() == Type.CLOSED_DOOR);
	}

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v is an opened door, else
	 *         false.
	 */
	public boolean isDoorOpened(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);
		Edge edge = this.getEdge(u, v);
		return (edge != null && edge.getType() == Type.OPENED_DOOR);
	}

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v allow passage, else
	 *         false.
	 */
	public boolean isNonBlocking(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);
		if (u == null)
			return false;
		Edge edge = this.getEdge(u, v);
		return (edge != null && (edge.getType() == Type.CORRIDOR || edge.getType() == Type.OPENED_DOOR));
	}

	private void calculateManhattanDistance(Vertex source, Vertex target) {
		Queue<Vertex> fifo = new ArrayDeque<Vertex>();
		target.setNbr(1);
		fifo.add(target);
		while (!fifo.isEmpty()) {
			Vertex actual = fifo.remove();
			boolean b = false;
			for (Directions dir : Directions.values()) {
				if (this.isNonBlocking(actual, dir)) {
					b = true;
					Vertex next = this.getNeighborVertex(actual, dir);
					if (next.getNbr() == 0) {
						next.setNbr(actual.getNbr() + 1);
						fifo.add(next);

					}
				}
			}
			if (!b)
				System.out.println("erreur edge vertex: " + actual.getX() + " ; " + actual.getY());
		}
	}

	public void launchManhattan(Vertex source, Vertex target) {
		for (Vertex vertex : this.vertexSet())
			vertex.setNbr(0);
		calculateManhattanDistance(source, target);
	}

	/**
	 * Give a Vertex took randomly
	 * @return Vertex from the labyrinth
	 */
	public Vertex getRandomVertex(){
		Vertex v = null;
		while(v == null) {
			int x = UsefulFunctions.generateRandomNumber(0, DefineClass.SOUTH_BORDER);
			int y = UsefulFunctions.generateRandomNumber(0, DefineClass.EAST_BORDER);
			v = getVertex(x,y);
		}
		return v;
	}

	public void toDot(String fileName) {
		try {
			PrintWriter file = new PrintWriter(fileName + ".dot");
			file.println("graph labyrinth {");
			for (Edge e : this.edgeSet()) {
				file.println(e.toDot());
			}
			file.println("}");
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
