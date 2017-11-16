import java.util.Map;

import org.jgrapht.VertexFactory;
import org.jgrapht.graph.SimpleGraph;

public class Graph<V,E> extends SimpleGraph<V,E> {

	public Graph(Class<? extends E> edgeClass) {
		super(edgeClass);

	}



}
