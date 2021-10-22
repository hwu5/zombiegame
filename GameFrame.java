
//*****************************************
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    static boolean replay = false;
    GameFrame(){

        this.setTitle("Solomon Treasure");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new GamePanel(this));
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);






    }


}
