package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class Images {
    static String pathImages = "Images/";
    public static Image imageDoor = new Image(pathImages + "door_open.png");
    public static Image imagePlayer = new Image(pathImages + "player.png");
    public static Image imageEnemy = new Image(pathImages + "bad.png");
    public static Image imageButtonClose = new Image(pathImages + "button_close.png");
    public static Image imageButtonOpen = new Image(pathImages + "button_open.png");
    public static Image imageDoorOpen = new Image(pathImages + "door_open.png");
    public static Paint paintWall = Paint.valueOf("red");
}
