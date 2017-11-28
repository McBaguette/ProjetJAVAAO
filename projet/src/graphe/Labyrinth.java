package graphe;

import model.DefineClass;
import model.DefineClass.Directions;
import model.DefineClass.Type;

import java.util.Set;

import org.jgrapht.graph.SimpleGraph;

@SuppressWarnings("serial")
public class Labyrinth extends SimpleGraph<Vertex, Edge> {

	public Labyrinth() {
		super(Edge.class);
	}


	public graphe.Vertex getVertex(int x, int y) {
		// On doit pouvoir se passer de cette m�thode il me semble (?)
		// Pour l'affichage, il suffira de parcourir le graphe
		// Et pour les d�placement, on � juste be soin d'acceder aux voisins
		// Si y'a d'autres cas auquel j'ai pas pens�, j'essayerais d'y remedier !
		/*
		 * j'avais ajouté cette méthode, pouvoir initialiser la position de joueurs,
		 * des monstres et des bonbons, sans avoir à chaque fois à parcourir la liste
		 * des sommets
		 */
		/*
		 * Parce que si sur un sommet il y a un bonbon, alors le sommet fait référence
		 * à un objetMap
		 */
		
		/*
		 * Dans la generation du prof, on accede a chaque fois juste aux voisins, du coup on doit pouvoir faire avec juste getNeighbors
		 * Et pour les bonbon/porte, on peut prendre un sommet au hasard avec le set
		 */
		Set<Vertex> setVertex = this.vertexSet();
		for (Vertex v : setVertex){
			if (v.getX() == x && v.getY() == y)
				return v;
		}
		return null;
	}

	public void buildLabyrinth(int numEdges) {
		// Emile: c'était pas dans le UML, mais je me dis que ce serait plus malin, de
		// générer un labyrinth en prenant en paramètre le nombre d'arêtes, et comme
		// ça tu retires aléatoirement des arêtes

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
		 * TODO : Verifier si l'ordre des coordonnees est le meme (nord->y- , ouest->x-,
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
			 * TODO : Optimiser si target ou source sont toujours les meme ?
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
