package view;

import controller.ControllerUser;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class View {

	public static final int SPAN = 4;	//  Pixels  for  a  unit
	public static final int WALL = 2;	//  thickness of the walls (in units)
	public static final int CELL = 9;	//  size of the cells (in units)
	static final Paint WALL_COLOR = Color.BURLYWOOD;
	static final Paint OP_DOOR_COLOR = Color.GREEN;
	static final Paint CL_DOOR_COLOR = Color.RED;
	static final Paint SCENE_COLOR = Color.WHITE;

	private static View instance = new View();
	private static Scene scene;
	private static Pane pane;
	private int w,h;

	private View(){
		pane = new Pane();
	}

	public static View getInstance(){
		return instance;
	}



	/**
	 * Call to initiliate the game
	 * @param stage	where draw cells
	 * @param nbX	number of cells in the labyrinth on x
	 * @param nbY   number of cells in the labyrinth on y
	 */
	private void initFrames(Stage stage, int nbX, int nbY){
		this.w = nbX;
		this.h = nbY;
		scene = new Scene(pane,
				((WALL+CELL)*nbX+WALL)*SPAN,
				((WALL+CELL)*nbY+WALL)*SPAN);
		scene.setFill(SCENE_COLOR);
		stage.setScene(scene);

		drawFrames();
	}
	
	private Rectangle createWall(int xs, int ys, int xt, int yt) {
		int x, y, xspan, yspan;
		if (ys == yt) {
			x = ((WALL + CELL) + (WALL + CELL) * ((int) (xs + xt) / 2)) * SPAN;
			y = (WALL + ys * (WALL + CELL)) * SPAN;
			xspan = WALL * SPAN;
			yspan = CELL * SPAN;
		} else if (xs == xt) {
			x = (WALL + xs * (WALL + CELL)) * SPAN;
			y = ((WALL + CELL) + (WALL + CELL) * ((int) (ys + yt) / 2)) * SPAN;
			xspan = CELL * SPAN;
			yspan = WALL * SPAN;
		} else {
			return null;
		}
		return new Rectangle(x, y, xspan, yspan);
	}

	/**
	 * Call to draws all cells
	 */
	private void drawFrames() {
		double wall_span = WALL * SPAN;
		double lab_width = w * (CELL + WALL);
		double lab_height = h * (CELL + WALL);
		
		Rectangle right = new Rectangle(0, 0, SPAN * (lab_width + WALL), wall_span);
		Rectangle down = new Rectangle(0, SPAN * lab_height, SPAN * (lab_width + WALL), wall_span);
		Rectangle top = new Rectangle(0, 0, wall_span, SPAN * (lab_height + WALL));
		Rectangle left = new Rectangle(SPAN * lab_width, 0, wall_span, SPAN * (lab_height + WALL));

		right.setFill(WALL_COLOR);
		down.setFill(WALL_COLOR);
		top.setFill(WALL_COLOR);
		left.setFill(WALL_COLOR);

		pane.getChildren().add(right);
		pane.getChildren().add(down);
		pane.getChildren().add(top);
		pane.getChildren().add(left);

		Rectangle square;
		
		for (int x = 0; x < w - 1; ++x) {
			for (int y = 0; y < h - 1; ++y) {
				square = new Rectangle(0, 0, WALL * SPAN, WALL * SPAN);
				square.setFill(WALL_COLOR);
				square.setX(((WALL + CELL) + (WALL + CELL) * x) * SPAN);
				square.setY(((WALL + CELL) + (WALL + CELL) * y) * SPAN);
				pane.getChildren().add(square);
			}
		}
	}

	/**
	 * Draw a wall between the cells s and t
	 */
	public void drawWall(int xs, int ys, int xt, int yt, boolean isDoor, boolean isOpen) {
		Rectangle square = createWall(xs, ys, xt, yt);
		if (!isDoor) {
			square.setFill(WALL_COLOR);
		} else {
			square.setFill(isOpen ? OP_DOOR_COLOR : CL_DOOR_COLOR);
		}
		pane.getChildren().add(square);
	}
	
	
	
	public void clear(){
		pane.getChildren().clear();
		drawFrames();
	}

	/**
	 * Call at the begining of the game, to create keyboard event and call start()
	 * @param stage Where to draw
	 * @param nbCellsX  The number of cells on abscissa
	 * @param nbCellsY  The number of cells on ordinate
	 */
	public void launch(Stage stage, int nbCellsX, int nbCellsY){

		start(stage, nbCellsX, nbCellsY);
		scene.setOnKeyPressed( ControllerUser.getInstance());
		scene.setOnKeyReleased(ControllerUser.getInstance());
	}

	/**
	 * Call by launch at the begining of the game to initiate the window.
	 * @param stage where to draw
	 * @param nbCellsX The number of cells on abscissa
	 * @param nbCellsY The number of cells on ordinate
	 */
	private void start(Stage stage, int nbCellsX, int nbCellsY){
		initFrames(stage, nbCellsX, nbCellsY);
		stage.setScene(scene);
		stage.show();
	}

	public Scene getScene(){
		return scene;
	}
	
	public Pane getPane(){
		return pane;
	}
}
