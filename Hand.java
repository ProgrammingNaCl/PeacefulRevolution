import java.util.LinkedList;

/**
 * Created by opilane on 21.07.2016.
 */
public class Hand {
    static LinkedList<Card> cards = new LinkedList<>();


    public Hand() {
        int handSize= GameData.defineHandSize();
        Main.deck =  GameData.generateDeck();
        LinkedList <Card> deck= Main.deck;
        for (int i = 0; i < handSize; i++) {
            cards.add( GameData.drawCard(deck));
        }

    }
    public static void removeCard(int i){
        cards.remove(i);



    }
     public static Card getCard(){

         return cards.getFirst();
     }
}
