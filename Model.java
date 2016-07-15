import java.awt.*;
import java.util.LinkedList;

/**
 * Created by diku on 10/16/15.
 */
public class Model {
    public boolean routeFinder(int[][] cityMap) {
        boolean demonstrationPathSecured = false;
        return demonstrationPathSecured;
    }

    public boolean isAccepted(Point location, String mappiece) {
        getNearbyMappieces(location);
        return false;
    }

    public String[] getNearbyMappieces(Point location) {
        String[] mappieces = {"", "", "", ""};
        int index = toIndex(location);
        //calculate each index around the mappiece
        int indextop = 0;
        int indexbottom = 0;
        int indexleft = 0;
        int indexright = 0;
        final int NONEXISTENT = -1;//indicates offmap zone

        if ((location.y - 1 == -1) && (location.x - 1 == -1)) {
            //LT corner
            indextop = NONEXISTENT;
            indexright = 1;
            indexbottom = 9;
            indexleft = NONEXISTENT;

        } else if ((location.y - 1 == -1) && (location.x + 1 == 9)) {
            //RT corner
            indextop = NONEXISTENT;
            indexright = NONEXISTENT;
            indexbottom = 17;
            indexleft = 7;
        } else if ((location.y + 1 == 5) && (location.x - 1 == -1)) {
            //LB corner
            indextop = 27;
            indexright = 37;
            indexbottom = NONEXISTENT;
            indexleft = NONEXISTENT;
        } else if ((location.y + 1 == 5) && (location.x + 1 == 9)) {
            //RB corner
            indextop = 35;
            indexright = NONEXISTENT;
            indexbottom = NONEXISTENT;
            indexleft = 43;
        } else {
            indextop = (location.y - 1) * 9 + location.x;
            indexright = location.y * 9 + location.x + 1;
            indexbottom = (location.y + 1) * 9 + location.x;
            indexleft = (location.y) * 9 + location.x - 1;
        }

        if (indextop == NONEXISTENT) mappieces[0] = "";
        else {
            mappieces[0] = String.valueOf(BoardData.cityMap[indextop]);
        }
        if (indexright == NONEXISTENT) mappieces[1] = "";
        else {
            mappieces[1] = String.valueOf(BoardData.cityMap[indexright]);
        }
        if (indexbottom == NONEXISTENT) mappieces[2] = "";
        else {
            mappieces[2] = String.valueOf(BoardData.cityMap[indexbottom]);
        }
        if (indexleft == NONEXISTENT) mappieces[3] = "";
        else {
            mappieces[3] = String.valueOf(BoardData.cityMap[indexleft]);
        }


        return mappieces;
    }

    private int toIndex(Point location) {
        return location.y * 9 + location.x;
    }

    public void distributeBounty() {
        //TODO this needs completion
        int nofPlayers = Main.nofPlayers;
        String winnerSide = "";
        for (int i = 0; i < nofPlayers; i++) {
            if (Main.players.get(i).id == Main.winnerID) {
                winnerSide = Main.players.get(i).allegiance;
                break;
            }
        }
        //select  arandom number of supporter cards valued 1-3
        LinkedList<String> bountyOfSupporters = new LinkedList<String>();
        int[] supporters;


        if (winnerSide == GameData.side[0]) {
            //share out the gold to loyalists

            //Collections.shuffle();
            for (PlayerData p : Main.players) {
                if (p.allegiance == GameData.side[0]) {
                    //int
                } else {
                }
            }
        } else if (winnerSide == GameData.side[1]) {
            for (PlayerData p : Main.players) {
                if (p.allegiance == GameData.side[1]) {
                    //int
                } else {
                }
            }
        }

    }

    public boolean isCompletedRoute() {
        Maze m = new Maze(Main.map);
        return m.solveMaze();
    }

    public static class Maze {
        int height = 5 * 3;
        int width = 9 * 3;
        int[][] maze = new int[width][height]; // The maze
        boolean[][] wasHere = new boolean[width][height];
        boolean[][] correctPath = new boolean[width][height]; // The solution to the maze
        int startX = 1;
        int startY = 7; // Starting X and Y values of maze
        int endX = 25;
        int endY = Main.presidentsLocation*3-2 ;     // Ending X and Y values of maze 1,7,13
        boolean b;
        public Maze(String[] map){
            generateMaze(map);
        }
        public boolean solveMaze() {
            //Read the map
            //default empty map
            String[] map = {
                    "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########",
                    "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########",
                    "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########",
                    "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########",
                    "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########", "#########"

            };
            for (int i = 0; i < 9 * 5; i++) {
                map[i] = Main.map[i];
            }

            maze = generateMaze(map); // Create Maze (1 = path, 2 = wall)
            for (int row = 0; row < width; row++) {
                // Sets boolean Arrays to default values
                for (int col = 0; col < height; col++) {

                    wasHere[row][col] = false;
                    correctPath[row][col] = false;
                }
            }
            //show maze

            b = recursiveSolve(startX, startY);

            // Will leave you with a boolean array (correctPath)
            // with the path indicated by true values.
            // If b is false, there is no solution to the maze
            return b;
        }


        public boolean recursiveSolve(int x, int y) {
            if (x == endX && y == endY) return true; // If you reached the end

            if(x < width && y<height) {
                if (maze[x][y] == 2 || wasHere[x][y]) return false;

                // If you are on a wall or already were here
                wasHere[x][y] = true;

                if (x != 0) // Checks if not on left edge
                    if (recursiveSolve(x - 1, y)) { // Recalls method one to the left
                        correctPath[x][y] = true; // Sets that path value to true;
                        return true;
                    }
                if (x != width - 1) // Checks if not on right edge
                    if (recursiveSolve(x + 1, y)) { // Recalls method one to the right
                        correctPath[x][y] = true;
                        return true;
                    }
                if (y != 0)  // Checks if not on top edge
                    if (recursiveSolve(x, y - 1)) { // Recalls method one up
                        correctPath[x][y] = true;
                        return true;
                    }
                if (y != height - 1) // Checks if not on bottom edge
                    if (recursiveSolve(x, y + 1)) { // Recalls method one down
                        correctPath[x][y] = true;
                        return true;
                    }
                return false;
            }else{return false;}
        }

        public int[][] generateMaze(String[] map) {
            int[][] cityAsMaze = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    cityAsMaze[x][y] = 2;
                }
            }


            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < 9; j++) {
                    Point ma = toMazeAddress(i,j);
                    if (map[i].equals("") ){
                        cityAsMaze[ma.x][ma.y] = 2;
                    }
                    else{
                        if( map[i].charAt(j) == '#'){
                            cityAsMaze[ma.x][ma.y] = 2;
                        }
                        else if(map[i].charAt(j) == ' '){
                            cityAsMaze[ma.x][ma.y] = 1;
                        }
                        else{}
                    }

                }

            }
            return cityAsMaze;
        }

        private Point toMazeAddress(int mapIndex, int indexOnMappiece) {

            Point ma = new Point();
            ma.y = ((mapIndex-(mapIndex%9))/9) * 3 +((indexOnMappiece-(indexOnMappiece%3))/3);
            ma.x = ((mapIndex%9)*3) +(indexOnMappiece%3);
            return ma;
        }
        private Point indexToPoint(int mapIndex){
            Point ma = new Point();
            ma.y = (mapIndex-(mapIndex%9))/9;
            ma.x = (mapIndex%9);
            return ma;
        }
        private int pointToIndex(Point mapAddress){
            return mapAddress.x *9 + mapAddress.y;
        }
    }
    public static class Actions{
        //TODO: actions
        //violence and guards
        //corruption and reporters
        //injustice and lawyers
        //hints
        public void getHint(boolean narking){
            if(narking){

            }
            else{

            }

        }

        static boolean informerSpeaks = false;
        public void setInformerSpeaks(){
            informerSpeaks = true;
        }
        public void setInformerSilent(){
            informerSpeaks = false;
        }

    }

}
