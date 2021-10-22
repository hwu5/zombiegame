import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovingCharacter {
    int x = 0;
    int y = 0;
    int id = 0;
    BufferedImage image = null;
    double covering = 0;
    MovingCharacter(int x, int y, String image, int covering){



        this.x = x;
        this.y = y;

        try {
            this.image = ImageIO.read(new File(image));


        }
        catch (IOException e) {
            System.out.println(image+" failed");
        }

        this.covering = covering;
    }



}
