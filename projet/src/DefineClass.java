public class DefineClass {
	private static DefineClass instance = new DefineClass();
	public static DefineClass getInstance() 
	{
		return instance;
	}
	private char NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
	private int TOP_BORDER = 0, WEST_BORDER = 0, SOUTH_BORDER = 20, EAST_BORDER = 20;

}
