package view;

import java.awt.Frame;
import java.util.List;

import javax.print.DocFlavor.URL;

import graphe.Labyrinth;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.IDeplacable;

public class View {

	static final int SPAN = 4;	//  Pixels  for  a  unit
	static final int WALL = 2;	//  thickness of the walls (in units)
	static final int CELL = 9;	//  size of the cells (in units)
	static final Paint WALL_COLOR = Color.BURLYWOOD;
	static final Paint SCENE_COLOR = Color.WHITE;

	private static View view;
	private static Scene scene;
	private static Pane pane;

	private View(){
		pane = new Pane();
	}

	public static View getInstance(){
		if(view==null){
			view = new View();
		}
		return view;
	}

	private static void drawFrame(Stage stage, int nbX, int nbY){
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
	private static void drawWall(int xs, int ys, int xt, int yt,
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

	private static void drawImage(String img, int x, int y){
		Image image = new Image(View.class.getResource(img).toExternalForm());
		ImageView imageView = new ImageView(image);
		pane.getChildren().add(imageView);
		double xt = (int)((WALL+x*(WALL+CELL))*SPAN);
		double yt = (int)((WALL+y*(WALL+CELL))*SPAN);
		imageView.setX(xt);
		imageView.setY(yt);
	}

	public static void draw(Labyrinth lab, IDeplacable player, List<IDeplacable> enemies){

	}

	public void launch(){

	}

	public void start(Stage stage){

		StackPane root = new StackPane();
		Scene scene = new Scene(root, 300, 250);
		stage.setScene(scene);
		stage.show();
	}

	public Scene getScene(){
		return scene;
	}
}
