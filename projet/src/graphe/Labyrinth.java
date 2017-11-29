package graphe;

import model.DefineClass;
import model.DefineClass.Directions;
import model.DefineClass.Type;

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
		 * Dans la generation du prof, on accede a chaque fois juste aux voisins, du
		 * coup on doit pouvoir faire avec juste getNeighbors Et pour les bonbon/porte,
		 * on peut prendre un sommet au hasard avec le set
		 */
		Set<Vertex> setVertex = this.vertexSet();
		for (Vertex v : setVertex) {
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
		Vertex v = new Vertex(0, 0);
		this.addVertex(v);
		GeneratePerfectLabyrinth(v);
	}

	private void GeneratePerfectLabyrinth(Vertex vertex) {
		// une liste al�eatoire des 4directions
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
		// pourchacunedecesdirections,onavanceenprofondeurd�abord
		for (int i = 0; i < 4; ++i) {
			Directions dir = directions[i];
			Vertex v2 = new Vertex(vertex, dir);
			System.out.printf("Check vertex %d/%d... ", v2.getX(), v2.getY());
			if (v2.inBorders() && this.getVertex(v2.getX(), v2.getY()) == null) {
				System.out.printf("Ok.\n");
				int x = vertex.getX();
				int y = vertex.getY();
				int xt = 0, yt = 0;
				switch (dir) {
				case NORTH:
					xt = x;
					yt = y - 1;
					break;
				case SOUTH:
					xt = x;
					yt = y + 1;
					break;
				case EAST:
					xt = x + 1;
					yt = y;
					break;
				case WEST:
					xt = x - 1;
					yt = y;
					break;
				}
				Vertex next = new Vertex(xt, yt);
				this.addVertex(next);
				this.addEdge(vertex, next);
				GeneratePerfectLabyrinth(next);
			} else {
				System.out.printf("Nope.\n");
			}
		}
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

		}
		return null;
	}

	private void calculateManhattanDistance(Vertex source, Vertex target){
		Queue<Vertex> fifo = new ArrayDeque<Vertex>();
		target.setNbr(1);
		fifo.add(target);
		while(!fifo.isEmpty()){
			Vertex actual = fifo.remove();
			for (Directions dir: Directions.values()){
				if (this.isNonBlocking(actual, dir)){
					Vertex next = this.getNeighborVertex(actual, dir);
					if (next.getNbr() == 0){
						next.setNbr(actual.getNbr() +1);
						if (!next.equals(source)){
							fifo.add(next);
						}
					}
				}
			}
		}
	}
	public void launchManhattan(Vertex source, Vertex target) {
		for (Vertex vertex: this.vertexSet())
			vertex.setNbr(0);
		calculateManhattanDistance(source, target);
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

		if (u == null){
			return  false;
		}
		return getEdge(v,u).getType() == Type.WALL;
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

	public void toDot(String fileName) {
		try {
			PrintWriter file = new PrintWriter(fileName + ".dot");
			file.println("graph labyrinth {");
			for (Vertex v : this.vertexSet()) {
				file.println(v.toDot());
			}
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
