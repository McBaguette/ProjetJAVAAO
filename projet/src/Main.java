/**
 * Created by jakod on 16/11/2017.
 */

public class Main {
    public static int p = 0;
    public static void main(String[] args) {
        Graph<Vertex, Edges> G = new Graph<Vertex, Edges>(Edges.class);
        Vertex pointDeDepart = new Vertex(10,10);
        G.addVertex(pointDeDepart);
        generationLabryrinth(G, pointDeDepart);


    }
    public static void generationLabryrinth(Graph<Vertex,Edges> G, Vertex v)
    {

        int direction = (int) (1 + (Math.random() * (4 - 1)));
        if (G.nbVertex >= 20*20)
            return;

        for (int i = 1; i <= 4; i++)
        {
            if (i == DefineClass.getInstance().NORTH)
            {
                Vertex v2 = new Vertex(v.getX(), v.getY()-1);
                if (!G.containsVertex(v2) && v2.getY() > DefineClass.getInstance().NORTH_BORDER){
                    G.addVertex(v2);
                    G.addEdge(v,v2);
                    generationLabryrinth(G,v2);
                }
            }
            else if (i == DefineClass.getInstance().EAST)
            {
                Vertex v2 = new Vertex(v.getX() + 1, v.getY() );
                if (!G.containsVertex(v2) && v2.getX() < DefineClass.getInstance().EAST_BORDER){
                    G.addVertex(v2);
                    G.addEdge(v,v2);
                    generationLabryrinth(G,v2);
                }

            }
            else if (i == DefineClass.getInstance().SOUTH)
            {
                Vertex v2 = new Vertex(v.getX(), v.getY()+1);
                if (!G.containsVertex(v2) && v2.getY() < DefineClass.getInstance().SOUTH_BORDER){
                    G.addVertex(v2);
                    G.addEdge(v,v2);
                    generationLabryrinth(G,v2);
                }
            }
            else
            {
                Vertex v2 = new Vertex(v.getX() - 1, v.getY());
                if (!G.containsVertex(v2) && v2.getX() > DefineClass.getInstance().WEST_BORDER){
                    G.addVertex(v2);
                    G.addEdge(v,v2);
                    generationLabryrinth(G,v2);
                }
            }
        }
    }
}
