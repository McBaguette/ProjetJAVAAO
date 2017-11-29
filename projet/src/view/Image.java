package view;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class Image {
    static String pathImages = "Images/";
    static ImageView imageDoor = new ImageView(pathImages + "door_open.png");
    static ImageView imagePlayer = new ImageView(pathImages + "player.png");
    static ImageView imageEnemy = new ImageView(pathImages + "bad.png");
    static ImageView imageButtonClose = new ImageView(pathImages + "button_close.png");
    static ImageView imageButtonOpen = new ImageView(pathImages + "button_open.png");
    static ImageView imageDoorOpen = new ImageView(pathImages + "door_open.png");
    static Paint paintWall = Paint.valueOf("red");
}
