import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StationCharacters{

    int x = 0;
    int y = 0;
    BufferedImage image = null;
    StationCharacters(int x, int y, String image){

        this.x = x;
        this.y = y;
        try {
            this.image = ImageIO.read(new File(image));


        }
        catch (IOException e) {
            System.out.println(image+" failed");
        }
    }
}
