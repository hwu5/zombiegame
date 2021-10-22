import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.ArrayList;

public class Zombie extends MovingCharacter{
    int id;
    static List<String> Zombies_loc_sta_lst = new ArrayList<String>();
    static List<Double> can_cross = new ArrayList<Double>();


    Zombie(int id, int x, int y, String image, int covering){
        super(x, y, image, covering);

        this.id = id;
        Zombies_loc_sta_lst.add(x+","+y);
        can_cross.add(0d);
    }

    public void setxy(int id, int x, int y){
        this.x = x;
        this.y = y;
        Zombies_loc_sta_lst.set(id, x+","+y);
    }

    public static int getZombieI(int xx, int yy){
        if (!Zombies_loc_sta_lst.contains(xx + "," + yy)) {
            return -404;
        }
        else {
            return Zombies_loc_sta_lst.indexOf(xx + "," + yy);
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
