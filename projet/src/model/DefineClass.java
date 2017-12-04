package model;

public class DefineClass {
	public enum Directions {
		NORTH, EAST, SOUTH, WEST
	};

	public enum Type {
		OPENED_DOOR, CLOSED_DOOR, CORRIDOR, WALL;
	};


	public static int HEIGHT = 20, WIDTH = 20;
	public static int NORTH_BORDER = 0, WEST_BORDER = 0, SOUTH_BORDER = HEIGHT - 1, EAST_BORDER = WIDTH - 1;
	public static int NUMBER_CANDIES_ON_MAP = 10;
	public static int NUMBER_CANDIES_TYPE = 4;

}