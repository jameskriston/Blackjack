import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {

    private static final Random rand = new Random();
    private static int max = 0;
    private static final int min = 0;

    public static ArrayList<String> makeDeck(){
    ArrayList<String> deck = new ArrayList<>();
    for (int i = 0; i < 24; i++) {
        deck.addAll(Arrays.asList("A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K")); // add 4 suits * 6 decks of Ace,2,3,...King to our deck
    }
    max = deck.size()-1;
    return deck;
}

public static String dealCard(ArrayList<String> deck){
        int randomIndex = rand.nextInt(max-min+1)+ min ;
        String card = deck.get(randomIndex);
        deck.remove(randomIndex);
        max--;
        return card;

}
}
