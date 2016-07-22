import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by muse on 27-Sep-15.
 */

//saanud sellest eriti aru //TODO: kommentida ära
public class PlayerData {
    static int cardInFocus =-1;
    String allegiance = "";
    String name = "";
    int supportGained = 0;
    boolean pass = false;
    public LinkedList<String> techHand = new LinkedList<String>();
    public LinkedList<String> hand = new LinkedList<String>();
    public LinkedList<String> blockers = new LinkedList<>();
     int id;

    //names
    public void setName() {

        for (int i = 1; i < Main.nofPlayers; i++) {
            this.name = "AI" + Integer.toString(i);
        }
    }

    public PlayerData(){
        //setAllegiance();
        setName();
        //TODO: setHand/ selectHandSize
        //TODO: drawCard
    }
    public static LinkedList<Card> drawCardHand(LinkedList <Card> cards){
        LinkedList <Card> cardHand= new LinkedList<>();
        int hs= GameData.getHandSize();
        for (int i=0; i < hs; i++ ){
            Card card= cards.getFirst();

            cardHand.add(card);
            cards.removeFirst();

        }

        return cardHand;


    }

}
