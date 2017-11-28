package graphe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;

import model.DefineClass.Type;

class LabyrinthTest {
	
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
	void test() {
		Labyrinth g = new Labyrinth();
		
		Vertex v = new Vertex(1,3);
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
		}
	}

}
