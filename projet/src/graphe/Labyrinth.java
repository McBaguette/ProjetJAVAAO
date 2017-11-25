package graphe;

import model.DefineClass;
import org.jgrapht.graph.SimpleGraph;

public class Labyrinth<Vertex, Edges> extends SimpleGraph<Vertex, Edges> {
	public int nbVertex;
	//en attendant de trouver quelque chose de générique:
	private graphe.Vertex arrayOfVertices[][];
	public Labyrinth(Class<? extends Edges> edgeClass) {
		super(edgeClass);
		arrayOfVertices = new graphe.Vertex[DefineClass.WIDTH][DefineClass.HEIGHT];
		nbVertex = 0;
	}

	public boolean addVertex(Vertex v) {
		if (super.addVertex(v)) {
			nbVertex++;
			arrayOfVertices[((graphe.Vertex)v).getX()][((graphe.Vertex)v).getY()] = ((graphe.Vertex)v);
			return true;
		}
		return false;
	}
	public graphe.Vertex getVertex(int x, int y){
		//comme on a besoin de récupérer le sommet pour placer les objets, et bien c'est pour l'instant un tableau, pour que ce soit plus rapide.
		//et il n'y a aucune méthode de jgrapht qui permette de récupérer un seul sommet, la seule chose qu'on a c'est de pouvoir récupérer tous les sommets
		return arrayOfVertices[x][y];
	}
	public void buildLabyrinth(int numEdges){
		//Emile: c'était pas dans le UML, mais je me dis que ce serait plus malin, de générer un labyrinth en prenant en paramètre le nombre d'arêtes, et comme ça tu retires aléatoirement des arêtes

		//TODO
	}
	private void GeneratePerfectLabyrinth(Vertex v) {

	}

	public Vertex getNeighborVertex(Vertex v, DefineClass.Directions dir){
		//Emile si tu vois ça, c'est que je l'ai pas implémenté et si tu veux le faire c'est en fait la méthode getVertexByDir du prof.
		//TODO
		return null;
	}

	public void launchManhattan(Vertex source, Vertex target) {
		//TODO
	}
}
