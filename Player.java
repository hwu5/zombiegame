import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.ArrayList;

public class Player extends MovingCharacter{
    int cracking_lock_id = -404;
    boolean crackinglock = false;
    BufferedImage cracked_im;
    int[] keylst = new int[10];
    int zombieDegree = 0;
    int treasure_count = 0;
    int woodblock_count = 0;
    boolean deadOfZombie = false;
    boolean beatGame = false;
    Date date;
    static List<Double> can_cross = new ArrayList<Double>();

    int free_block = 0;

    Player(int x, int y, String image, int covering){
        super(x, y, image, covering);
        can_cross.add(4.1);
        can_cross.add(6d);
        can_cross.add(0d);


    }


    public boolean equals(int x2, int y2){

        if (this.x == x2 && this.y == y2){
            return true;
        }
        else{
            return false;

        }
    }





    public static double[] getCancross(){
        double[] lstCanCross = new double[can_cross.size()];
        for (int i = 0; i < can_cross.size(); i++){
            lstCanCross[i] = can_cross.get(i);
        }
        return lstCanCross;
    }



}
