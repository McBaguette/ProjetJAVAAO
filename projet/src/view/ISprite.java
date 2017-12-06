package view;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by clement on 06/12/2017.
 */
public interface ISprite {
    public void draw(Pane panel, int x, int y);
    public void removeImageFromView();
    public void setImage(Image image);
}
