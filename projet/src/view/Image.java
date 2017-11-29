package view;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class Image {
    static String pathImages = "Images/";
    static ImageView imageDoor = new ImageView(pathImages + "door_open.png");
    static Paint paintWall = Paint.valueOf("red");
}
