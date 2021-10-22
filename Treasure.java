import java.util.ArrayList;
import java.util.List;

public class Treasure extends StationCharacters{

    int id;
    static List<String> Treasures_loc_sta_lst = new ArrayList<String>();
    boolean pickedup = false;
    Treasure(int id, int x, int y, String image){

        super(x, y, image);//

        this.id = id;
        Treasures_loc_sta_lst.add(x+","+y);
    }
    public static int getTreasureI(int xx, int yy){
        if (!Treasures_loc_sta_lst.contains(xx + "," + yy)) {
            return -404;
        }
        else {
            return Treasures_loc_sta_lst.indexOf(xx + "," + yy);
        }
    }
    public void pickup(int id){
        if (this.id == id){
            pickedup = true;
            //System.out.println("wrong id ->" + id + "right id->"+this.id);
        }
        else{
            //System.out.println("wrong id ->" + id + "right id->"+this.id);
        }
    }
}
