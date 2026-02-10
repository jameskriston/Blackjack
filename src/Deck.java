import java.util.*;

public class Deck {

    private static final Random rand = new Random(); // for pulling random cards
    private static int max = 0; // max of how many cards in our deck
    private static final int min = 0; // min of 0 obviously

    // makeDeck() - make a 6 deck * 52 card (312) deck of Ace-King
    // Returns - ArrayList<String> deck - returns our deck arraylist
    // suits don't matter in most forms of blackjack outside occasional side bets or players card counting
    // for that reason im implementing a deck with no suit feature
    public static ArrayList<String> makeDeck(){ //
    ArrayList<String> deck = new ArrayList<>();

    for (int i = 0; i < 24; i++) {
        deck.addAll(Arrays.asList("A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K")); // add 4 suits * 6 decks of Ace,2,3,...King to our deck
    }
    max = deck.size()-1; // store # cards in deck as max
    Collections.shuffle(deck); // shuffle for randomness
    return deck;
}

    // dealCard() - deal card by removing from deck, adding to a hand
    // Parameters - ArrayList<String> deck - a deck to deal from
    // Returns - String card - return card to the callee
    public static String dealCard(ArrayList<String> deck){
        int randomIndex = rand.nextInt(max-min+1)+ min ; // get random index with Random() class for extra randomness
        String card = deck.get(randomIndex); // get the card at the random index, store in our return string
        deck.remove(randomIndex); // remove card from deck to mimic real probability of each card value
        max--; // decrement deck arraylist to avoid out of bounds array access
        return card;
}
}
