package model;
public class DefineClass {
	private static DefineClass instance = new DefineClass();
	public static DefineClass getInstance() 
	{
		return instance;
	}
	public int NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
	public int NORTH_BORDER = 0, WEST_BORDER = 0, SOUTH_BORDER = 20, EAST_BORDER = 20;

}