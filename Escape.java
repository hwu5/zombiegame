import java.util.ArrayList;
import java.util.List;

public class Escape extends StationCharacters{

    static List<String> Escapes_loc_sta_lst = new ArrayList<String>();
    int id;
    Escape(int id, int x, int y, String image) {
        super(x, y, image);
        this.id = id;
        Escapes_loc_sta_lst.add(x+","+y);
    }
    public static int getEscapeI(int xx, int yy){
        if (!Escapes_loc_sta_lst.contains(xx + "," + yy)) {
            return -404;
        }
        else {
            return Escapes_loc_sta_lst.indexOf(xx + "," + yy);
        }
    }
}
