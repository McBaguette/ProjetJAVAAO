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

	static final int SPAN = 4;	//  Pixels  for  a  unit
	static final int WALL = 2;	//  thickness of the walls (in units)
	static final int CELL = 9;	//  size of the cells (in units)
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
	
	/**
	 * Call to draws all cells
	 */
	private void drawFrames(){
		Rectangle square;
		square = new Rectangle(0,0,
				SPAN* (w*(CELL+WALL)+WALL), WALL*SPAN);
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);


		square = new Rectangle(0,SPAN*(h*(CELL+WALL)),
				SPAN* (w*(CELL+WALL)+WALL), WALL*SPAN);
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		square = new Rectangle(0,0,
				WALL*SPAN, SPAN* (h*(CELL+WALL)+WALL));
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		square = new Rectangle(SPAN* (w*(CELL+WALL)) ,0,
				WALL*SPAN, SPAN* (h*(CELL+WALL)+WALL));
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		for(int x=0; x<w-1; ++x){
			int offsetX = ((WALL+CELL) + (WALL+CELL)*x)*SPAN;
			for(int y=0;y<h-1;++y){
				int offsetY = ((WALL+CELL) +(WALL+CELL)*y)*SPAN;
				square = new Rectangle(offsetX, offsetY,
						WALL*SPAN, WALL*SPAN);
				square.setFill(WALL_COLOR);
				pane.getChildren().add(square);
			}
		}
	}

	/**
	 * Draw a wall between the cells s and t
	 */
	public void drawWall(int xs, int ys, int xt, int yt){
		int x = 0, y = 0, xspan = 0, yspan = 0;
		if(ys==yt){
			x = ((WALL+CELL)+(WALL+CELL)*((int)(xs+xt)/2))*SPAN;
			y = (WALL + ys*(WALL+CELL)) * SPAN;
			xspan = WALL*SPAN;
			yspan = CELL*SPAN;
			Rectangle square = new Rectangle(x,y,xspan, yspan);
			square.setFill(WALL_COLOR);
			pane.getChildren().add(square);
		}
		else if(xs==xt){
			x = (WALL+xs*(WALL+CELL))*SPAN;
			y = ((WALL+CELL)+(WALL+CELL)*((int)(ys+yt)/2))*SPAN;
			xspan = CELL*SPAN;
			yspan = WALL*SPAN;
			Rectangle square = new Rectangle(x,y,xspan, yspan);
			square.setFill(WALL_COLOR);
			pane.getChildren().add(square);
		}
	}
	
	/**
	 * Draw a door between the cells s and t
	 */
	public void drawDoor(int xs, int ys, int xt, int yt, boolean open){
		int x = 0, y = 0, xspan = 0, yspan = 0;
		Paint color = open?OP_DOOR_COLOR:CL_DOOR_COLOR;
		if(ys==yt){
			x = ((WALL+CELL)+(WALL+CELL)*((int)(xs+xt)/2))*SPAN;
			y = (WALL + ys*(WALL+CELL)) * SPAN;
			xspan = WALL*SPAN;
			yspan = CELL*SPAN;
			Rectangle square = new Rectangle(x,y,xspan, yspan);
			square.setFill(color);
			pane.getChildren().add(square);
		}
		else if(xs==xt){
			x = (WALL+xs*(WALL+CELL))*SPAN;
			y = ((WALL+CELL)+(WALL+CELL)*((int)(ys+yt)/2))*SPAN;
			xspan = CELL*SPAN;
			yspan = WALL*SPAN;
			Rectangle square = new Rectangle(x,y,xspan, yspan);
			square.setFill(color);
			pane.getChildren().add(square);
		}
	}

	/**
	 * Set the ImageView image to the coordonate x,y.
	 * @param image the ImageView to set
	 * @param x coordonate will be recalculate to be drawn on screen
	 * @param y coordonate will be recalculate to be drawn on screen
	 */
	public void drawImageView(ImageView image, int x, int y){
		double xt = (int)((WALL+x*(WALL+CELL))*SPAN);
		double yt = (int)((WALL+y*(WALL+CELL))*SPAN);
		image.setX(xt);
		image.setY(yt);
	}

	/**
	 * Add an ImageView to the Pane pane
	 * @param image ImageView to add
	 */
	public void addImageView(ImageView image){
		pane.getChildren().add(image);
	}
	/**
	 * Remove an ImageView to the Pane pane
	 * @param image ImageView to remove
	 */
	public void removeImageView(ImageView image){
		if (image != null)
			pane.getChildren().remove(image);
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
}
