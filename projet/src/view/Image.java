package view;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class Image {
    static String pathImages = "Images/";
    public static ImageView imageDoor = new ImageView(pathImages + "door_open.png");
    public static ImageView imagePlayer = new ImageView(pathImages + "player.png");
    public static ImageView imageEnemy = new ImageView(pathImages + "bad.png");
    public static ImageView imageButtonClose = new ImageView(pathImages + "button_close.png");
    public static ImageView imageButtonOpen = new ImageView(pathImages + "button_open.png");
    public static ImageView imageDoorOpen = new ImageView(pathImages + "door_open.png");
    public static Paint paintWall = Paint.valueOf("red");
}
