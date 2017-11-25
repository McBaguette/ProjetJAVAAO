package graphe;

import model.DefineClass;
import model.DefineClass.Directions;

import java.util.Set;

import org.jgrapht.graph.SimpleGraph;

import graphe.Edge.Type;

@SuppressWarnings("serial")
public class Labyrinth extends SimpleGraph<Vertex, Edge> {

	public Labyrinth() {
		super(Edge.class);
	}

	/**
	 * @return Vertex at pos x:y
	 *
	 * @deprecated Ne devrais plus être utilisé, on ne devrais pas avoir à acceder à
	 *             un vertex par ses coordonées
	 */
	public graphe.Vertex getVertex(int x, int y) {
		// On doit pouvoir se passer de cette méthode il me semble (?)
		// Pour l'affichage, il suffira de parcourir le graphe
		// Et pour les déplacement, on à juste be soin d'acceder aux voisins
		// Si y'a d'autres cas auquel j'ai pas pensé, j'essayerais d'y remedier !
		return null;
	}

	public void buildLabyrinth(int numEdges) {
		// Emile: c'Ã©tait pas dans le UML, mais je me dis que ce serait plus malin, de
		// gÃ©nÃ©rer un labyrinth en prenant en paramÃ¨tre le nombre d'arÃªtes, et comme
		// Ã§a tu retires alÃ©atoirement des arÃªtes

		// TODO
	}

	private void GeneratePerfectLabyrinth(Vertex v) {

	}

	/**
	 * @param v
	 *            Vertex
	 * @param dir
	 *            Direction
	 * @return vertex in the direction dir from v.
	 */
	public Vertex getNeighborVertex(Vertex v, DefineClass.Directions dir) {
		/*
		 * TODO : Vérifier si l'ordre des coordonnées est le même (nord->y- , ouest->x-,
		 * ect)
		 */
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
			/*
			 * TODO : Optimiser si target ou source sont toujours les même ?
			 */
			if (e.getSource().getX() == targetX && e.getSource().getY() == targetY)
				return e.getSource();
			if (e.getTarget().getX() == targetX && e.getTarget().getY() == targetY)
				return e.getSource();
		}
		return null;
	}

	public void launchManhattan(Vertex source, Vertex target) {
		// TODO
	}

	/**
	 * @param v
	 *            : Vertex
	 * @param dir
	 *            : Direction
	 * @return True is the edge in the direction dir from v is a wall, else false.
	 */
	public boolean isWall(Vertex v, Directions dir) {
		Vertex u = this.getNeighborVertex(v, dir);
		Edge edge = this.getEdge(u, v);
		return (edge == null);
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
		Edge edge = this.getEdge(u, v);
		return (edge != null && (edge.getType() == Type.CORRIDOR || edge.getType() == Type.OPENED_DOOR));
	}

}
