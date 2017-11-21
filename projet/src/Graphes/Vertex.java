package Graphes;

public class Vertex {

    public Vertex(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    private int x,y;

    public boolean equals(Object obj) {
        Vertex v = (Vertex)obj;
        return x == v.getX() && y == v.getY();
    }
    public int compareTo(Vertex o) {

        if( o.getX() == this.x && o.getY() == this.y)
            return 0;
        return -1;
    }
    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }
}

