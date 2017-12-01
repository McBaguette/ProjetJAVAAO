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
	static final Paint SCENE_COLOR = Color.WHITE;

	private static View instance = new View();
	private static Scene scene;
	private static Pane pane;

	private View(){
		pane = new Pane();
	}

	public static View getInstance(){
		return instance;
	}

	/**
	 * Call to initiliate the game, to draws all cells
	 * @param stage	where draw cells
	 * @param nbX	number of cells in the labyrinth on x
	 * @param nbY   number of cells in the labyrinth on y
	 */
	private void drawFrames(Stage stage, int nbX, int nbY){
		scene = new Scene(pane,
				((WALL+CELL)*nbX+WALL)*SPAN,
				((WALL+CELL)*nbY+WALL)*SPAN);
		scene.setFill(SCENE_COLOR);
		stage.setScene(scene);

		Rectangle square;
		square = new Rectangle(0,0,
				SPAN* (nbX*(CELL+WALL)+WALL), WALL*SPAN);
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);


		square = new Rectangle(0,SPAN*(nbY*(CELL+WALL)),
				SPAN* (nbX*(CELL+WALL)+WALL), WALL*SPAN);
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		square = new Rectangle(0,0,
				WALL*SPAN, SPAN* (nbY*(CELL+WALL)+WALL));
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		square = new Rectangle(SPAN* (nbX*(CELL+WALL)) ,0,
				WALL*SPAN, SPAN* (nbY*(CELL+WALL)+WALL));
		square.setFill(WALL_COLOR);
		pane.getChildren().add(square);

		for(int x=0; x<nbX-1; ++x){
			int offsetX = ((WALL+CELL) + (WALL+CELL)*x)*SPAN;
			for(int y=0;y<nbY-1;++y){
				int offsetY = ((WALL+CELL) +(WALL+CELL)*y)*SPAN;
				square = new Rectangle(offsetX, offsetY,
						WALL*SPAN, WALL*SPAN);
				square.setFill(WALL_COLOR);
				pane.getChildren().add(square);
			}
		}
	}

	/*
	 * Draw a wall between the cells s and t
	 */
	public void drawWall(int xs, int ys, int xt, int yt,
								Paint color){
		int x = 0, y = 0, xspan = 0, yspan = 0;
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


	public void drawImageView(ImageView image, int x, int y){
		double xt = (int)((WALL+x*(WALL+CELL))*SPAN);
		double yt = (int)((WALL+y*(WALL+CELL))*SPAN);
		image.setX(xt);
		image.setY(yt);
	}

	public void addImageView(ImageView image){
		pane.getChildren().add(image);
	}
	public void removeImageView(ImageView image){
		pane.getChildren().remove(image);
	}

	public void launch(Stage stage, int nbCellsX, int nbCellsY){

		start(stage, nbCellsX, nbCellsY);
		scene.setOnKeyPressed( ControllerUser.getInstance());
		scene.setOnKeyReleased(ControllerUser.getInstance());
	}
	private void start(Stage stage, int labyrinthWidth, int labyrinthHeight){

		StackPane root = new StackPane();
		drawFrames(stage, labyrinthWidth, labyrinthHeight);
		stage.setScene(scene);
		stage.show();
	}

	public Scene getScene(){
		return scene;
	}
}
