package com.codewithhowie;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.image.DataBufferByte;



public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 500;
    final int MAZE_WIDTH = 700;
    final int MAZE_HEIGHT = 300;

    final int UNIT_SIZE = 50;
    final int SCREEN_WIDTH_unit = SCREEN_WIDTH/UNIT_SIZE;
    final int SCREEN_HEIGHT_unit = SCREEN_HEIGHT/UNIT_SIZE;
    final int GAME_UNITS = (MAZE_WIDTH*MAZE_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int maxBoundX = 1000;
    final int maxBoundY = 1500;
    final int leave_out = 5;
    int BoundX;
    int BoundY;
    //player
    double player_covering = 0;
    Player playeri;
    //zombie
    List<Zombie> Zombies_lst = new ArrayList<Zombie>();
    int zombieInfectInterval = 1000;
    //treasure
    List<Treasure> treasures_lst = new ArrayList<Treasure>();
    //escape
    List<Escape> escapes_lst = new ArrayList<Escape>();
    //woodblock
    List<WoodBlock> woodblocks_lst = new ArrayList<WoodBlock>();
    static final double[][] mazeMap_ori = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,5,7,7,4,0,1,1,1,1,1,1,0,0,5,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,5,0,1},
            {1,2,4.1,7,0,0,0,1,0,1,1,0,1,0,3,0,0,3,0,1,0,1,3,1,0,1},
            {1,1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,5,0,0,1,0,0,0,0,0,1},
            {1,0,0,1,0,1,0,1,0,1,1,1,1,0,0,0,0,0,0,1,3,0,0,0,3,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1,1,1},
            {1,0,0,0,3,1,0,0,6,5,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
            {1,0,0,1,0,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,5,0,1},
            {1,0,0,0,0,1,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,1,1,0,1},
            {1,1,1,0,0,1,4,1,1,1,1,0,5,0,1,1,1,0,0,1,0,0,0,1,0,1},
            {1,5,4,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };
    double[][] maze_map;

    //key
    List<Lock> locks_lst = new ArrayList<Lock>();

    int key_unit = 25;
    int win_key_x = 350;
    int win_key_y = 450;
    int win_lock_x = 80;
    int win_lock_y = 450;

    //others
    boolean running = false;
    boolean replay = false;
    boolean showHelp = true;
    Timer timer;
    Random random;
    JButton buttonexit;
    GameFrame thisFrameOut;
    //images
    BufferedImage bricks_img = null;
    BufferedImage ground_img = null;
    BufferedImage badEnding_img = null;
    BufferedImage goodEnding_img = null;
    BufferedImage playAgain_img = null;
    BufferedImage helpImg = null;
    BufferedImage f1Img = null;


    public static Dictionary[] add_array_dict(int n, Dictionary arr[], Dictionary x)
    {
        int i;

        // create a new array of size n+1

        Dictionary newarr[] = new Dictionary[n + 1];
        //System.out.println(n);
        if (n > 0) {
            for (i = 0; i < n; i++)
                newarr[i] = arr[i];


        }
        newarr[n] = x;

        return newarr;
    }

    public static int[][] add_array_int_arr(int n, int arr[][], int x[])
    {
        int i;

        // create a new array of size n+1

        int newarr[][] = new int[n + 1][];
        //System.out.println(n);
        if (n > 0) {
            for (i = 0; i < n; i++){
                newarr[i] = arr[i];

            }


        }
        newarr[n] = x;

        return newarr;
    }

    GamePanel(GameFrame thisF){
        random = new Random();
        //buttonexit = new JButton("message");

        //buttonexit.addActionListener(this);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MyMouseEvent());
        //this.add(buttonexit);
        thisFrameOut = thisF;



        startGame();
    }
    public void startGame() {

        try {
            bricks_img = ImageIO.read(new File("C:\\Users\\hhh43\\IdeaProjects\\test_gui\\brick.png"));
        }
        catch (IOException e) {
            System.out.println("read brick import error");
        }
        try {
            ground_img = ImageIO.read(new File("ground_mini_game.png"));
        }
        catch (IOException e) {
            System.out.println("ground_mini_game import error");
        }

        try {
            badEnding_img = ImageIO.read(new File("badEnding.png"));
        }
        catch (IOException e) {
            System.out.println("badEnding import error");
        }

        try {
            goodEnding_img = ImageIO.read(new File("goodEnding.png"));
        }
        catch (IOException e) {
            System.out.println("goodEnding import error");
        }

        try {
            playAgain_img = ImageIO.read(new File("playAgain.png"));
        }
        catch (IOException e) {
            System.out.println("playAgain import error");
        }


        try {
            helpImg = ImageIO.read(new File("help.png"));
        }
        catch (IOException e) {
            System.out.println("f1 import error");
        }

        try {
            f1Img = ImageIO.read(new File("f1.png"));
        }
        catch (IOException e) {
            System.out.println("help import error");
        }


        maze_map = mazeMap_ori.clone();
        System.out.println("start game check"+maze_map[3][1]);
        System.out.println("start game check final"+mazeMap_ori[3][1]);

        //make lists of NPCs and locks, and init player
        Zombies_lst.clear();
        locks_lst.clear();
        treasures_lst.clear();
        escapes_lst.clear();
        woodblocks_lst.clear();

        Zombie.Zombies_loc_sta_lst.clear();
        Lock.loc_arr_static.clear();
        Treasure.Treasures_loc_sta_lst.clear();
        Escape.Escapes_loc_sta_lst.clear();
        WoodBlock.WoodBlocks_loc_sta_lst.clear();

        BoundX = 0;
        BoundY = 0;

        int treasure_id = 0;
        int lock_id = 0;
        int zombie_id = 0;
        int escape_id = 0;
        int woodblock_id = 0;
        for(int yy=0; yy<mazeMap_ori.length; yy++){
            for(int xx=0; xx<mazeMap_ori[yy].length; xx++){
                /*
                menu
                0 -> road
                1 -> wall
                2 -> player
                3 -> zombie
                4 -> lock
                5 -> treasure
                6 -> escape
                 */
                //System.out.println(xx+", "+yy);

                if (maze_map[yy][xx] == 2){
                    playeri = new Player(xx, yy, "player.jpg", 0);
                    System.out.println("maploc: "+xx+", "+yy);
                    System.out.println("playerloc: "+playeri.x+","+playeri.y);
                }

                else if (maze_map[yy][xx] == 3){
                    //covid
                    Zombie this_Zombie = new Zombie(zombie_id, xx, yy, "zombie_on_ground.png", 0);

                    Zombies_lst.add(this_Zombie);
                    zombie_id += 1;

                }
                else if (maze_map[yy][xx] == 4 || maze_map[yy][xx] == 4.1){
                    //lock
                    String this_uncracked_im =  "C:\\Users\\hhh43\\IdeaProjects\\test_gui\\lock.png";
                    String this_cracked_im =  "C:\\Users\\hhh43\\IdeaProjects\\test_gui\\unlock.png";
                    Lock this_lock = new Lock(lock_id, xx, yy, 5, this_uncracked_im, this_cracked_im);
                    locks_lst.add(this_lock);



                    lock_id += 1;
                }
                else if (maze_map[yy][xx] == 5){
                    //Treasure

                    Treasure this_treasure = new Treasure(treasure_id, xx, yy, "sunflower.png");
                    treasures_lst.add(this_treasure);



                    treasure_id += 1;
                }
                else if (maze_map[yy][xx] == 6){
                    //Escape

                    Escape this_escape = new Escape(escape_id, xx, yy, "nether-portal.gif");
                    escapes_lst.add(this_escape);




                    escape_id += 1;
                }
                else if (maze_map[yy][xx] == 7){
                    //WoodBlock

                    WoodBlock this_woodblock = new WoodBlock(woodblock_id, xx, yy, "woodblock.png");
                    woodblocks_lst.add(this_woodblock);




                    woodblock_id += 1;
                }

            }
        }


        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        //System.out.println("paintComponent");
        draw(g);
    }

    public void draw(Graphics g) {
        //System.out.println("draw");







            for(int xx = 0; xx< maze_map[0].length;xx++) {
                for(int yy = 0; yy< maze_map.length;yy++) {
                    /*
                    menu
                    0 -> road
                    1 -> wall
                    2 -> player
                    3 -> zombie
                    4 -> lock
                    5 -> treasure
                     */

                    if (maze_map[yy][xx] == 0){
                        //g.setColor(Color.green);
                        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        g.drawImage(ground_img,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                    }
                    else if (maze_map[yy][xx] == 1){
                        g.drawImage(bricks_img,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);
                        //g.setColor(new Color(100,100,0)); //light green
                        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

                        //System.out.println("wall success");

                    }
                    else if (maze_map[yy][xx] == 2){
                        //g.setColor(Color.green);
                        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        g.drawImage(playeri.image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                    }


                    //Zombies
                    else if (maze_map[yy][xx] == 3){

                        g.drawImage(Zombies_lst.get(Zombie.getZombieI(xx, yy)).image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                        //g.setColor(Color.red);

                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

                    }
                    //locks uncracked
                    else if (maze_map[yy][xx] == (double) 4){

                        g.drawImage(locks_lst.get(Lock.getLockI(xx, yy)).uncracked_im,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);
                        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));


                    }
                    //locks cracked
                    else if (maze_map[yy][xx] == (double) 4.1){
                        g.drawImage(locks_lst.get(Lock.getLockI(xx, yy)).cracked_im,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);
                        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));


                    }
                    //treasure
                    else if (maze_map[yy][xx] == 5){
                        g.drawImage(ground_img,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);
                        g.drawImage(treasures_lst.get(Treasure.getTreasureI(xx, yy)).image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                        //g.setColor(Color.yellow);

                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

                    }
                    //escape
                    else if (maze_map[yy][xx] == 6){
                        //System.out.println(treasures_lst.get(0).x+","+treasures_lst.get(0).y);
                        //System.out.println(xx+","+yy);
                        g.drawImage(escapes_lst.get(Escape.getEscapeI(xx, yy)).image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                        //g.setColor(Color.green);

                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

                    }
                    //woodblock
                    else if (maze_map[yy][xx] == 7){
                        g.drawImage(woodblocks_lst.get(0).image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);
                        //g.drawImage(woodblocks_lst.get(WoodBlock.getWoodBlockI(xx, yy)).image,(xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE , this);

                        //g.setColor(Color.orange);

                        //g.fillRect((xx-BoundX)*UNIT_SIZE, (yy-BoundY)*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

                    }
                }
            }

            g.setColor(Color.yellow);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Treausre count: "+playeri.treasure_count, (MAZE_WIDTH - metrics.stringWidth("Treausre count: "+1))/2, g.getFont().getSize());


            //g.setColor(Color.red);
            //g.setFont( new Font("Ink Free",Font.BOLD, 40));
            //FontMetrics metrics = getFontMetrics(g.getFont());
            //g.drawString("covid_degree: "+covid_degree, (MAZE_WIDTH - metrics.stringWidth("covid_degree: "+covid_degree))/2, //g.getFont().getSize());


            if (playeri.cracking_lock_id != -404){
                g.setColor(new Color(12,12,122)); //
                //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(0, MAZE_HEIGHT, MAZE_WIDTH, SCREEN_HEIGHT);

                g.setColor(Color.green);
                g.drawLine(win_lock_x, win_lock_y+key_unit,
                        (locks_lst.get(playeri.cracking_lock_id).locklst.length)*key_unit+win_lock_x, win_lock_y+key_unit);

                g.drawLine(win_lock_x, win_lock_y,
                        win_lock_x, win_lock_y+key_unit);
                g.drawLine(locks_lst.get(playeri.cracking_lock_id).locklst.length*key_unit+win_lock_x, win_lock_y,
                        locks_lst.get(playeri.cracking_lock_id).locklst.length*key_unit+win_lock_x, win_lock_y+key_unit);

                for (int ii = 0; ii<locks_lst.get(playeri.cracking_lock_id).locklst.length; ii++){
                    g.setColor(Color.green);
                    int xx = ii*key_unit+win_key_x;
                    int yy = win_key_y;
                    //g.fillRect(xx, yy, key_unit, key_unit);
                    int[] locklst = locks_lst.get(playeri.cracking_lock_id).locklst;
                    for (int ii_y = 0; ii_y<locklst[ii]; ii_y++){

                        if (ii == 0){
                            g.drawLine(ii*key_unit+win_lock_x, win_lock_y-ii_y*key_unit,
                                    ii*key_unit+win_lock_x, win_lock_y-(ii_y+1)*key_unit);

                        }
                        else {
                            if (locklst[ii-1]<ii_y+1){
                                g.drawLine(ii*key_unit+win_lock_x, win_lock_y-ii_y*key_unit,
                                        ii*key_unit+win_lock_x, win_lock_y-(ii_y+1)*key_unit);

                            }

                        }

                        if (ii == locklst.length-1){
                            g.drawLine((ii+1)*key_unit+win_lock_x, win_lock_y-ii_y*key_unit,
                                    (ii+1)*key_unit+win_lock_x, win_lock_y-(ii_y+1)*key_unit);

                        }
                        else {
                            if (ii_y+1>locklst[ii+1]){
                                g.drawLine((ii+1)*key_unit+win_lock_x, win_lock_y-ii_y*key_unit,
                                        (ii+1)*key_unit+win_lock_x, win_lock_y-(ii_y+1)*key_unit);

                            }
                        }



                        //g.fillRect(xx, yy-(ii_y+1)*key_unit, key_unit, key_unit);


                        //g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, MAZE_HEIGHT);
                        //g.drawLine(0, i*UNIT_SIZE, MAZE_WIDTH, i*UNIT_SIZE);


                    }
                    g.setColor(Color.green);
                    g.drawLine(ii*key_unit+win_lock_x, win_lock_y-locklst[ii]*key_unit,
                            (ii+1)*key_unit+win_lock_x, win_lock_y-locklst[ii]*key_unit);
                    //System.out.println(ii+ "bot:"+ii*key_unit+win_lock_x);
                    //System.out.println(ii+ "bot:"+ii*key_unit+win_lock_x);
                }



                g.setColor(Color.yellow);
                for (int ii = 0; ii<playeri.keylst.length; ii++){
                    int xx = ii*key_unit+win_key_x;
                    int yy = win_key_y;
                    g.fillRect(xx, yy, key_unit, key_unit);
                    for (int ii_y = 0; ii_y<playeri.keylst[ii]; ii_y++){
                        g.fillRect(xx, yy-(ii_y+1)*key_unit, key_unit, key_unit);


                    }
                }


            }

            if(playeri.deadOfZombie) {
                String message = Integer.toString(playeri.treasure_count);
                g.drawImage(badEnding_img,(MAZE_WIDTH - badEnding_img.getWidth())/2, (MAZE_HEIGHT) / 2-20, this);

                g.setColor(Color.green);
                g.setFont( new Font("Helvetica",Font.BOLD, 40));

                g.drawString(message, MAZE_WIDTH / 2 + 110, (int) (MAZE_HEIGHT) / 2 + 45);




            }
            else if(playeri.beatGame){
                String message = Integer.toString(playeri.treasure_count);
                int height = (int)(((double) badEnding_img.getWidth()/goodEnding_img.getWidth()) * goodEnding_img.getHeight());

                g.drawImage(goodEnding_img,(MAZE_WIDTH - badEnding_img.getWidth())/2, (MAZE_HEIGHT) / 2-20, badEnding_img.getWidth(), height,  this);

                g.setColor(Color.green);
                g.setFont( new Font("Helvetica",Font.BOLD, 40));

                g.drawString(message, MAZE_WIDTH / 2 - 80, (int) (MAZE_HEIGHT) / 2 + 89);
            }

        if(showHelp) {

            g.drawImage(helpImg,0, 0, this);

            timer.stop();
        }
        else{
            g.drawImage(f1Img,0, 0, this);
        }



    }
    public int[] Aroundcoord(int coordX, int coordY, double characterNum){
        int[] characterCord = {-1, -1};
        if (maze_map[coordY][coordX+1] == characterNum) {
            characterCord = new int[]{coordX + 1, coordY};
        }
        else if (maze_map[coordY][coordX-1] == characterNum) {
            characterCord = new int[]{coordX - 1, coordY};
        }
        else if (maze_map[coordY+1][coordX] == characterNum) {
            characterCord = new int[]{coordX, coordY + 1};
        }
        else if (maze_map[coordY-1][coordX] == characterNum) {
            characterCord = new int[]{coordX, coordY - 1};
        }
        else if (maze_map[coordY-1][coordX-1] == characterNum) {
            characterCord = new int[]{coordX - 1, coordY - 1};
        }
        else if (maze_map[coordY-1][coordX+1] == characterNum) {
            characterCord = new int[]{coordX + 1, coordY - 1};
        }
        else if (maze_map[coordY+1][coordX+1] == characterNum) {
            characterCord = new int[]{coordX + 1, coordY + 1};
        }
        else if (maze_map[coordY+1][coordX-1] == characterNum) {
            characterCord = new int[]{coordX - 1, coordY + 1};
        }

        return characterCord;
    }



    public void zombieMove(){
        for (int i = 0; i<Zombies_lst.size(); i++){
            if (random.nextInt(3) == 0) { //this controls aprox how often the zombies move, random adds more uncertainty

                //create a list of possible future locations of this zombie
                Zombie this_zombie = Zombies_lst.get(i);
                boolean found_player = false;

                //player on the left
                int zombie_chaseP_x = -404;
                int zombie_chaseP_y = -404;

                for(int xx = this_zombie.x-1; xx > 0; xx--){
                    if (maze_map[this_zombie.y][xx] == 2){
                        found_player = true;
                        zombie_chaseP_x = this_zombie.x-1;
                        zombie_chaseP_y = this_zombie.y;
                        break;
                    }
                    else if(maze_map[this_zombie.y][xx] == 3){
                        continue;
                    }
                    else if(maze_map[this_zombie.y][xx] != 0){
                        break;
                    }
                }

                for(int xx = this_zombie.x+1; xx < maze_map[0].length; xx++){
                    if (maze_map[this_zombie.y][xx] == 2){
                        found_player = true;
                        zombie_chaseP_x = this_zombie.x+1;
                        zombie_chaseP_y = this_zombie.y;
                        break;
                    }
                    else if(maze_map[this_zombie.y][xx] == 3){
                        continue;
                    }
                    else if(maze_map[this_zombie.y][xx] != 0){
                        break;
                    }
                }

                for(int yy = this_zombie.y-1; yy > 0; yy--){
                    if (maze_map[yy][this_zombie.x] == 2){
                        found_player = true;
                        zombie_chaseP_x = this_zombie.x;
                        zombie_chaseP_y = this_zombie.y-1;
                        break;
                    }
                    else if(maze_map[yy][this_zombie.x] == 3){
                        continue;
                    }
                    else if(maze_map[yy][this_zombie.x] != 0){
                        break;
                    }
                }

                for(int yy = this_zombie.y+1; yy < maze_map.length; yy++){
                    if (maze_map[yy][this_zombie.x] == 2){
                        found_player = true;
                        zombie_chaseP_x = this_zombie.x;
                        zombie_chaseP_y = this_zombie.y+1;
                        break;
                    }
                    else if(maze_map[yy][this_zombie.x] == 3){
                        continue;
                    }
                    else if(maze_map[yy][this_zombie.x] != 0){
                        break;
                    }
                }

                int finalZombie_chaseP_x = zombie_chaseP_x;
                int finalZombie_chaseP_y = zombie_chaseP_y;


                if(found_player){
                    if (DoubleStream.of(Zombie.getCancross()).anyMatch(ii -> ii == maze_map[finalZombie_chaseP_y][finalZombie_chaseP_x])){
                        maze_map[(int) this_zombie.y][(int) this_zombie.x] = (double) this_zombie.covering;
                        Zombies_lst.get(i).covering = maze_map[zombie_chaseP_y][zombie_chaseP_x];
                        Zombies_lst.get(i).setxy(i, zombie_chaseP_x, zombie_chaseP_y);

                        maze_map[(int) Zombies_lst.get(i).y][(int) Zombies_lst.get(i).x] = 3;

                    }
                }
                else{

                    List<int[]> coord_arr = new ArrayList<int[]>();

                    coord_arr.add(new int[]{(int) this_zombie.x, (int) this_zombie.y});
                    if (DoubleStream.of(Zombie.getCancross()).anyMatch(ii -> ii == maze_map[this_zombie.y][this_zombie.x+1])){

                        coord_arr.add(new int[]{(int) this_zombie.x+1, (int) this_zombie.y});

                    }
                    if (DoubleStream.of(Zombie.getCancross()).anyMatch(ii -> ii == maze_map[this_zombie.y][this_zombie.x - 1])) {
                        coord_arr.add(new int[]{(int) this_zombie.x-1, (int) this_zombie.y});
                    }
                    if (DoubleStream.of(Zombie.getCancross()).anyMatch(ii -> ii == maze_map[this_zombie.y + 1][this_zombie.x])) {

                        coord_arr.add(new int[]{(int) this_zombie.x, (int) this_zombie.y+1});

                    }
                    if (DoubleStream.of(Zombie.getCancross()).anyMatch(ii -> ii == maze_map[this_zombie.y - 1][this_zombie.x])) {

                        coord_arr.add(new int[]{(int) this_zombie.x, (int) this_zombie.y-1});
                    }
                    /*
                    0 ¬
                       |
                    0 -> none
                    1 -> cross
                    3 -> full
                    1 | 2 | 3
                    ---------
                    4 | 5 | 6
                    ---------
                    7 | 8 | 9
                     */
                    int move_idx = random.nextInt(coord_arr.size());
                    //System.out.println(Arrays.deepToString(coord_arr.toArray()));

                    maze_map[(int) this_zombie.y][(int) this_zombie.x] = (double) this_zombie.covering;
                    Zombies_lst.get(i).covering = maze_map[coord_arr.get(move_idx)[1]][coord_arr.get(move_idx)[0]];
                    Zombies_lst.get(i).setxy(i, coord_arr.get(move_idx)[0], coord_arr.get(move_idx)[1]);

                    maze_map[(int) Zombies_lst.get(i).y][(int) Zombies_lst.get(i).x] = 3;
                }


                //System.out.println("this_zombie"+this_zombie.x +", "+ this_zombie.y);
                //System.out.println("Zombies_lst"+Zombies_lst.get(i).x +", "+ Zombies_lst.get(i).y);
            }
        }
    }
    public void checkZombieStatus() {
        if (playeri.zombieDegree == 0 && Aroundcoord(playeri.x, playeri.y, 3)[0] != -1){
            playeri.zombieDegree = 1;
            playeri.date = new Date();
            //System.out.println(playeri.date.toString());
        }

        if (playeri.zombieDegree >= 1 && new Date().getTime() - playeri.date.getTime() >= zombieInfectInterval){
            //System.out.println(new Date().toString());
            //System.out.println(new Date().getTime() - playeri.date.getTime());
            playeri.zombieDegree++;
        }

        if (playeri.zombieDegree>=3){
            running = false;
            playeri.deadOfZombie = true;
        }

        if(!running) {
            timer.stop();


        }


    }

    public void checkAroundLock(){

        if (Aroundcoord(playeri.x, playeri.y, 4 /* lock*/)[0] == -1) {
            playeri.cracking_lock_id = -404;
            playeri.crackinglock = false;
        }
    }



    public void unlock(int lock_id, int oper, int pos_hori, int pos_height) {

        if (oper == 1){
            if (0<=pos_height && pos_height<5 && 0<=pos_hori && pos_hori<10){
                playeri.keylst[pos_hori] = pos_height;
                boolean wrong_key = false;
                for (int ii=0; ii<playeri.keylst.length; ii++) {
                    if (playeri.keylst[ii] != locks_lst.get(lock_id).locklst[ii]) {
                        wrong_key = true;
                    }
                }
                if (!wrong_key) {
                    System.out.println("cracked");
                    locks_lst.get(lock_id).lock_cracked = true;
                    maze_map[locks_lst.get(lock_id).y][locks_lst.get(lock_id).x] = (double) 4.1;
                }

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent a) {

        if(running) {
            checkAroundLock();
            //checkHitCovid();
            checkZombieStatus();
            //checklock();
            zombieMove();
        }


        repaint();
        //System.out.println("out");
        //System.out.println(maze_map[3][1]);
        /*
        String buttonName = a.getActionCommand();
        System.out.println(buttonName);

        if (buttonName != null) {
            switch (buttonName) {
                case "message":
                    System.out.println("clicked");
                    break;
                default:
                    System.out.println("default was called in button switch, should not have happened");
                    break;
            }
        }
        //System.out.println("out");
        */
    }

    public class MyMouseEvent extends MouseAdapter {

        @Override
        public void	mousePressed(MouseEvent e) {
            //if the player is trying to crack a lock
            if (playeri.crackinglock){

                System.out.println("coord: "+ (e.getX() - win_key_x)/key_unit + ", " + Math.abs((e.getY()-win_key_y)/key_unit-1));

                unlock(playeri.cracking_lock_id, 1, (e.getX() - win_key_x)/key_unit, Math.abs((e.getY()-win_key_y)/key_unit-1));
                //find out how many units the player was trying to give the key
            }
        }
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    //System.out.println("AA");

                    if (DoubleStream.of(playeri.getCancross()).anyMatch(ii -> ii == maze_map[playeri.y][playeri.x - 1])) {
                        //System.out.println("DD " + playeri.x + ", " + playeri.y + ", " + maxBoundX);


                        if (BoundX + (maze_map[0].length - BoundX) / 2 >= playeri.x - 1 && BoundX - 1 >= 0) {
                            BoundX -= 1;
                        }
                        maze_map[playeri.y][playeri.x] = playeri.covering;
                        playeri.covering = maze_map[playeri.y][playeri.x - 1];
                        maze_map[playeri.y][playeri.x - 1] = 2;
                        playeri.x = playeri.x - 1;
                        //System.out.println(playeri.x + ", " + playeri.y);
                        //System.out.println(BoundX + ", " + BoundY);

                    }
                    break;
                case KeyEvent.VK_D:
                    //System.out.println("DD " + playeri.x + ", " + playeri.y + ", " + maze_map[0].length);
                    if (DoubleStream.of(playeri.getCancross()).anyMatch(ii -> ii == maze_map[playeri.y][playeri.x + 1])) {
                        if (SCREEN_WIDTH_unit / 2 <= playeri.x + 1 && BoundX + SCREEN_WIDTH_unit <= maze_map[0].length - 1) {
                            BoundX += 1;
                        }
                        maze_map[playeri.y][playeri.x] = playeri.covering;
                        playeri.covering = maze_map[playeri.y][playeri.x + 1];
                        maze_map[playeri.y][playeri.x + 1] = 2;
                        playeri.x = playeri.x + 1;
                        //System.out.println(playeri.x + ", " + playeri.y);
                        //System.out.println(BoundX + ", " + BoundY);

                    }
                    break;
                case KeyEvent.VK_W:
                    if (DoubleStream.of(playeri.getCancross()).anyMatch(ii -> ii == maze_map[playeri.y - 1][playeri.x])) {
                        if (BoundX + (maze_map[0].length + leave_out - BoundX) / 2 >= playeri.y - 1 && BoundY - 1 >= 0) {
                            BoundY -= 1;
                        }
                        maze_map[playeri.y][playeri.x] = playeri.covering;
                        playeri.covering = maze_map[playeri.y - 1][playeri.x];
                        maze_map[playeri.y - 1][playeri.x] = 2;
                        playeri.y = playeri.y - 1;
                        //System.out.println(playeri.x + ", " + playeri.y);
                        //System.out.println(BoundX + ", " + BoundY);

                    }
                    break;
                case KeyEvent.VK_S:
                    if (DoubleStream.of(playeri.getCancross()).anyMatch(ii -> ii == maze_map[playeri.y + 1][playeri.x])) {
                        //System.out.println("getCancross"+ Zombie.getCancross().toString());
                        if (SCREEN_HEIGHT_unit / 2 <= playeri.y + 1 && BoundY + SCREEN_HEIGHT_unit <= maze_map.length + leave_out - 1) {
                            BoundY += 1;
                        }
                        maze_map[playeri.y][playeri.x] = playeri.covering;
                        playeri.covering = maze_map[playeri.y + 1][playeri.x];
                        maze_map[playeri.y + 1][playeri.x] = 2;
                        playeri.y = playeri.y + 1;
                        //System.out.println(playeri.x + ", " + playeri.y);
                        //System.out.println(BoundX + ", " + BoundY);


                    }
                    break;
                case KeyEvent.VK_C:
                    //pick up
                    if (Aroundcoord(playeri.x, playeri.y, 5)[0] != -1){
                        int this_treasure_id = Treasure.getTreasureI(Aroundcoord(playeri.x, playeri.y, 5)[0], Aroundcoord(playeri.x, playeri.y, 5)[1]);
                        treasures_lst.get(this_treasure_id).pickup(this_treasure_id);
                        maze_map[treasures_lst.get(this_treasure_id).y][treasures_lst.get(this_treasure_id).x] = 0;
                        playeri.treasure_count += 1;
                        //System.out.println(playeri.treasure_count);
                    }
                    break;
                case KeyEvent.VK_K:
                    if (maze_map[playeri.y+1][playeri.x] == 0){
                        if (playeri.woodblock_count > 0) {

                            maze_map[playeri.y+1][playeri.x] = 7;
                            playeri.woodblock_count -= 1;

                        }
                    }
                    else if (maze_map[playeri.y+1][playeri.x] == 7){
                        maze_map[playeri.y+1][playeri.x] = 0;
                        playeri.woodblock_count += 1;
                    }
                    break;
                case KeyEvent.VK_I:
                    if (maze_map[playeri.y-1][playeri.x] == 0){
                        if (playeri.woodblock_count > 0) {

                            maze_map[playeri.y-1][playeri.x] = 7;
                            playeri.woodblock_count -= 1;

                        }
                    }
                    else if (maze_map[playeri.y-1][playeri.x] == 7){
                        maze_map[playeri.y-1][playeri.x] = 0;
                        playeri.woodblock_count += 1;
                    }
                    break;
                case KeyEvent.VK_J:
                    if (maze_map[playeri.y][playeri.x-1] == 0){
                        if (playeri.woodblock_count > 0) {

                            maze_map[playeri.y][playeri.x-1] = 7;
                            playeri.woodblock_count -= 1;

                        }
                    }
                    else if (maze_map[playeri.y][playeri.x-1] == 7){
                        maze_map[playeri.y][playeri.x-1] = 0;
                        playeri.woodblock_count += 1;
                    }
                    break;
                case KeyEvent.VK_L:
                    if (maze_map[playeri.y][playeri.x+1] == 0){
                        if (playeri.woodblock_count > 0) {

                            maze_map[playeri.y][playeri.x+1] = 7;
                            playeri.woodblock_count -= 1;

                        }
                    }
                    else if (maze_map[playeri.y][playeri.x+1] == 7){
                        maze_map[playeri.y][playeri.x+1] = 0;
                        playeri.woodblock_count += 1;
                    }
                    break;
                case KeyEvent.VK_O:
                    //start cracking a lock
                    if (Aroundcoord(playeri.x, playeri.y, 4)[0] != -1) {

                        System.out.println(locks_lst.get(0).x+","+locks_lst.get(0).y);
                        System.out.println(playeri.x+","+playeri.y);

                        int this_lock_i = Lock.getLockI(Aroundcoord(playeri.x, playeri.y, 4)[0],
                                Aroundcoord(playeri.x, playeri.y, 4)[1]);

                        locks_lst.get(this_lock_i).lock_opened = true;
                        playeri.crackinglock = true;
                        playeri.cracking_lock_id = this_lock_i;

                    }
                    break;
                case KeyEvent.VK_R:


                    if (Escape.getEscapeI(playeri.x, playeri.y) != -404) {
                        System.out.println("final score: " + playeri.treasure_count);
                        playeri.beatGame = true;
                        running = false;
                    }
                    break;

                case KeyEvent.VK_F1:
                    System.out.println("showHelp: "+showHelp);
                    if(showHelp){
                        showHelp = false;
                        timer.start();

                    }
                    else{
                        showHelp = true;
                    }
                    break;

                case KeyEvent.VK_V:
                    maze_map[playeri.y][playeri.x] = playeri.covering;

                    playeri.x = 1;
                    playeri.y = 3;

                    maze_map[playeri.y][playeri.x] = 2;
                    playeri.zombieDegree = 0;

                    BoundX = 0;
                    BoundY = 0;
                    playeri.deadOfZombie = false;
                    startGame();
                    //running = true;
                    //timer.start();


                    break;

                default:
                    break;
            }
        }
    }
}
