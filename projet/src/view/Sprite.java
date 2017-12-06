package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by clement on 06/12/2017.
 */
public class Sprite implements ISprite{
    private ImageView imageView;
    private Pane panel;

    public Sprite(Image img) {
        imageView = new ImageView(img);
        panel = null;
    }

    @Override
    public void draw(Pane panel, int x, int y) {
        addImageToView(panel);
        imageView.setX(x);
        imageView.setY(y);

    }
    private void addImageToView(Pane panel){
        if (this.panel == null){
            this.panel = panel;
            panel.getChildren().add(imageView);
        }
    }
    public void removeImageFromView(){
        if (this.panel != null){
            this.panel.getChildren().remove(imageView);
            this.panel = null;
        }

    }
    public void setImage(Image image){
        imageView.setImage(image);
    }
}
