package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.View;

/**
 * Created by clement on 06/12/2017.
 */
public class Sprite implements ISprite{
    private ImageView imageView;

    public Sprite(Image img) {
        imageView = new ImageView(img);

    }

    @Override
    public void draw(int x, int y) {
        //addImageToView(panel);
    	double xt = (int)((View.WALL+x*(View.WALL+View.CELL))*View.SPAN);
		double yt = (int)((View.WALL+y*(View.WALL+View.CELL))*View.SPAN);
        imageView.setX(xt);
        imageView.setY(yt);
    }
    public void addImageToView(Pane panel){
            panel.getChildren().add(imageView);
        
    }
    public void removeImageFromView(Pane panel){
            panel.getChildren().remove(imageView);

    }
    public void setImage(Image image){
        imageView.setImage(image);
    }
}
