import java.util.LinkedList;

/**
 * Created by opilane on 21.07.2016.
 */
public class Hand {
    static LinkedList<Card> cards;


    public Hand() {
        int handSize= GameData.defineHandSize();
        LinkedList <Card> deck= GameData.getDeck();
        for (int i = 0; i < handSize; i++) {
            GameData.drawCard(deck);
            cards.add( GameData.drawCard(deck));
        }

    }
    public static void removeCard(int i){
        cards.remove(i);



    }

}
