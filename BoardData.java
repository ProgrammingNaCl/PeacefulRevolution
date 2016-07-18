import java.awt.*;

/**
 * Created by muse on 27-Sep-15.
 */
public class BoardData {
    //näitab kaardi konsoolis
    public static int[][] cityMap = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
    };
    //linnakaart konsoolis
    public static String[][] cityMapAsMappieces = {
            {"         ","         ","         ","         ","         "},
            {"         ","         ","         ","         ","         "},
            {"         ","         ","         ","         ","         "},
            {"         ","         ","         ","         ","         "},
            {"         ","         ","         ","         ","         "}
    };

    public void setCityMap(int cityMapIndex, Point location) {
        this.cityMap[location.x][location.y] = cityMapIndex;
    }
    public void setCityMap(String mappiece, Point location) {
        this.cityMapAsMappieces[location.x][location.y] = mappiece;
    }

}
