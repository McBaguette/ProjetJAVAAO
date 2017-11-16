public class Vertex implements Comparable<Vertex>{

	public Vertex(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	private int x,y;

	@Override
	public int compareTo(Vertex o) {
		if( o.x == this.x && o.y == this.y)
			return 0;
		return -1;
	}

}
