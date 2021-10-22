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

public class Lock{
    int x = 0;
    int y = 0;
    int id;
    BufferedImage uncracked_im;
    BufferedImage cracked_im;
    boolean lock_opened = false;
    boolean lock_cracked = false;
    int keylen = 10; //number of lock units
    int[] locklst = new int[keylen];
    Random random = new Random();
    static int lock_num = 0;
    static List<String> loc_arr_static = new ArrayList<String>();

    //4
    //4.1

    Lock(int id, int x, int y, int keylen, String uncracked_im, String cracked_im){

        this.x = x;
        this.y = y;
        this.keylen = keylen;
        this.lock_opened = false;
        this.lock_cracked = false;

        try {
            this.uncracked_im = ImageIO.read(new File(uncracked_im));
            this.cracked_im = ImageIO.read(new File(cracked_im));


        }
        catch (IOException e) {
            System.out.println("failed");
        }


        for (int ii = 0; ii<keylen; ii++){

            locklst[ii] = random.nextInt(5);
        }
        lock_num += 1;
        this.id = id;
        loc_arr_static.add(x+","+y);
    }
    public static int getLockI(int xx, int yy){
        if (!Lock.loc_arr_static.contains(xx + "," + yy)){
            return -404;
        }
        else {
            return Lock.loc_arr_static.indexOf(xx+","+yy);
        }
    }
    public boolean checklock(int[] keylst){
        for (int i = 0; i < keylst.length; i++){
            if (keylst[i] != locklst[i]){
                return false;
            }
        }
        return true;
    }










}
