package graphe;


import java.util.Set;

import model.DefineClass;
import model.Deplacable;
import org.jgrapht.graph.SimpleGraph;

import model.DefineClass.Type;
import org.junit.Test;

import static junit.framework.Assert.fail;

public class LabyrinthTest {
	void printGraph(SimpleGraph<Vertex, Edge> g) {
		
		Set<Vertex> V = g.vertexSet();
		for(Vertex v : V) {
			//System.out.printf("Sommet en %d:%d\n", v.getX(), v.getY());
			for(Vertex u : V) {
				if(!v.equals(u) && g.getEdge(u, v)!=null) {
					//System.out.printf("\t - sommet en %d:%d\n", u.getX(), u.getY());
				}	
			}
		}
		
	}
	
	@Test
	public void test() {
		Labyrinth g = new Labyrinth();
		
		/*Vertex v = new Vertex(1,3);
		Vertex v2 = new Vertex(5,2);
		Vertex v3 = new Vertex(4,1);
		
		g.addVertex(v);
		g.addVertex(v2);
		g.addVertex(v3);
		
		g.addEdge(v, v2, new Edge(Type.CORRIDOR));
		g.addEdge(v, v3, new Edge(Type.CORRIDOR));
		
		printGraph(g);

		Edge ref = g.getEdge(v2, v);
		if(!g.containsVertex(v3)){
			fail("Should contain this vertex.");
		}
		if(!g.containsEdge(ref)){
			fail("Should contain this edge.");
		}*/
		g.buildLabyrinth(0);
		System.out.println(g.vertexSet().size() + " - " + g.edgeSet().size());
		g.toDot("laby");
		Set<Vertex> vset = g.vertexSet();


		for(Vertex v : vset) {
			if(g.edgesOf(v).size()>4) fail("Erreur nb edge.");
		}


		g = new Labyrinth();
		g.buildLabyrinth(0);

		System.out.println("Results Manhattan: ");
		g.launchManhattan(g.getVertex(0,0), g.getVertex(1,1));
		for (int y = 0; y < DefineClass.HEIGHT; y++){
			for (int x = 0; x < DefineClass.WIDTH; x++){
				System.out.print(g.getVertex(x,y).getNbr() + " ");
				if(g.getVertex(x,y).getNbr() == 0)
					fail("Manhattan fail");
			}
			System.out.print("\n");
		}
	}

}
