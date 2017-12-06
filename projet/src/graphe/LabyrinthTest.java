package graphe;


import java.util.Set;

import model.DefineClass;
import model.Deplacable;
import org.jgrapht.graph.SimpleGraph;

import model.DefineClass.Type;
import org.junit.Test;

import static junit.framework.Assert.fail;

public class LabyrinthTest {
	
	@Test
	public void test() {
		Labyrinth g = new Labyrinth();
		
		Vertex v1 = new Vertex(1,3);
		Vertex v2 = new Vertex(5,2);
		Vertex v3 = new Vertex(4,1);
		
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		
		//g.addEdge(v1, v2, new Edge(Type.CORRIDOR));
		//g.addEdge(v1, v3, new Edge(Type.CORRIDOR));
		g.addEdge(v1, v2);
		g.addEdge(v1,v3);
		g.addEdge(v1,v2);
		Edge ref = g.getEdge(v2, v1);
		if(!g.containsVertex(v3)){
			fail("Should contain this vertex. (by ref)");
		}
		if(!g.containsVertex(new Vertex(4,1))){
			fail("Should contain this vertex. (by coord)");
		}
		if(!g.containsEdge(ref)){
			fail("Should contain this edge.");
		}
		
		
		
		g = new Labyrinth();
		g.buildLabyrinth();
		System.out.println(g.vertexSet().size() + " - " + g.edgeSet().size());
		g.toDot("laby");
		Set<Vertex> vset = g.vertexSet();


		for(Vertex v : vset) {
			if(g.edgesOf(v).size()>4) fail("Erreur nb edge.");
		}



	}
	@Test
	public void testManhattan(){
		Labyrinth g = new Labyrinth();
		g.buildLabyrinth();

		System.out.println("Results Manhattan: ");
		g.launchManhattan(g.getVertex(0,0), g.getVertex(0,0));
		g.toDot("Manhattandot");
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
