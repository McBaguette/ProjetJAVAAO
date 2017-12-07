package model;

/**
 * Created by clement on 07/12/2017.
 */
public class UsefulFunctions {
    public static int generateRandomNumber(int inf, int sup){
         return inf + (int)(Math.random() * ((sup - inf) + 1));
    }
}
