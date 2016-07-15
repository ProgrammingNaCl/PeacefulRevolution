import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by muse on 27-Sep-15.
 */
public class GameData {
    final static String side[] = {"loyalist", "activist"};

    final static int[] supportCardsForWinners = {3,4,5,6,7,8,9,9};
    static String[] supportCard = {"1","1","1","1", "1","1","1","1", "1","1","1","1", "1","1","1","1", "2","2","2","2", "2","2","2","2", "3","3","3","3"};
    static LinkedList<String> supportBounty = new LinkedList<String>();

    final static int[] nofActivists = {3,4,4,5,5,6,7,7};
    final static int[] nofLoyalists = {1,1,2,2,3,3,3,4};
    static int numberOfPlayers = 3;
    static boolean deckDepleted = false;

    static boolean routeExists = false;
    static LinkedList<String[]> gameHistory =  new LinkedList<>();

    static int currentPlayer = 0;
    //MAP bits
    static String mapPieceHIDDEN = "HHHHHHHHH";

    static String mapPiece4Exits = "# #   # #";

    static String mapPiece3Exits = "#»#   # #";

    static String mapPiece2ExitsStraight = "#»#   #»#";

    static String mapPiece2ExitsTurn = "#»#  »# #";

    static String mapPiece1Exit = "#»#  »#»#";

    static String mapPieceNoExit = "# # » # #";

    static String mapPieceStart = "# #   # #";

    static String mapPiecePalace = "# #   # #";

    static String mapPiecePresident = "# # P # #";

    static String mapPieceHouses = "#########";

    public static String[] routeShape = {


            //home
            mapPieceStart,


//5 with 4 exits
            mapPiece4Exits,
            mapPiece4Exits,
            mapPiece4Exits,
            mapPiece4Exits,
            mapPiece4Exits,
//10 with 3 exits
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
            mapPiece3Exits,
//7 with straight
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
            mapPiece2ExitsStraight,
//9 with a turn
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
            mapPiece2ExitsTurn,
//with dead ends
            mapPiece1Exit,
            mapPiece1Exit,
//with a block
            mapPieceNoExit,
            mapPieceNoExit,
            mapPieceNoExit,
            mapPieceNoExit,
            mapPieceNoExit,
            mapPieceNoExit,
            mapPieceNoExit,




    };

    public static String[] techCards = {
            "corruption",
            "corruption",
            "violence",
            "violence",
            "ignorance",
            "ignorance",

            //solutions
            "reporter",
            "reporter",
            "police",
            "police",
            "lawyer",
            "lawyer",
            "reporter&police",
            "lawyer&reporter",
            "police&lawyer",
            //
            "burnt house",
            "burnt house",
            "burnt house",
            //hint
            "hint",
            "hint",
            "hint",
            "hint"

    };



    public static int getCurrentPlayer() {

        return currentPlayer;
    }

    public static int nextPlayer(){
        if(currentPlayer < numberOfPlayers)currentPlayer++;
        else {currentPlayer = 0;}
        return getCurrentPlayer();
    }

    public GameData() {
        LinkedList<String> deck = new LinkedList<String>();
        deck = generateDeck();

        for(int i =0;i < supportCard.length;i++)
        {
            supportBounty.add(supportCard[i]);
        }
        Collections.shuffle(supportBounty);
    }

    public static LinkedList<String> generateDeck() {
        LinkedList<String> d = new LinkedList<String>();
        for (String s : routeShape) {
            d.add(s);
        }
        for (String t : techCards) {
            d.add(t);
        }

        Collections.shuffle(d);
        return d;
    }

    public static String drawCard(LinkedList<String> deck) {
        String topCard = deck.getFirst();
        deck.removeFirst();
        return topCard;
    }
    public static LinkedList<PlayerData> activistsList(LinkedList<PlayerData> p){
        LinkedList<PlayerData> activists = new LinkedList<PlayerData>();
        for (int i = 0; i < p.size(); i++) {
            if(p.get(i).allegiance.equals("activist")){
                activists.add(p.get(i));
            }
        }

        return activists;
    }
    public static LinkedList<PlayerData> loyaliststsList(LinkedList<PlayerData> p){
        LinkedList<PlayerData> loyalists = new LinkedList<PlayerData>();
        for (int i = 0; i < p.size(); i++) {
            if(p.get(i).allegiance.equals("loyalist")){
                loyalists.add(p.get(i));
            }
        }

        return loyalists;
    }
}
