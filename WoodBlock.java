import java.util.ArrayList;
import java.util.List;

public class WoodBlock extends StationCharacters{

    int id;
    static List<String> WoodBlocks_loc_sta_lst = new ArrayList<String>();
    boolean pickedup = false;


    WoodBlock(int id, int x, int y, String image){

        super(x, y, image);

        this.id = id;
        WoodBlocks_loc_sta_lst.add(x+","+y);
    }

    public static int getWoodBlockI(int xx, int yy){
        if (!WoodBlocks_loc_sta_lst.contains(xx + "," + yy)) {
            return -404;
        }
        else {
            return WoodBlocks_loc_sta_lst.indexOf(xx + "," + yy);
        }
    }


    public void pickup(int id){
        if (this.id == id){
            pickedup = true;
            //System.out.println("WOODBLOC wrong id ->" + id + "right id->"+this.id);
        }
        else{
            //System.out.println("WOODBLOC wrong id ->" + id + "right id->"+this.id);
        }
    }
}
