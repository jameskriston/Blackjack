import java.util.ArrayList;

// hand class for working with card hands as Arraylists
public class Hand {

    // compute total score of hand
    // Parameters - ArrayList<String> hand - array of cards as string
    // Returns - integer total - total score of a hand as an int
    // treats ace as 11, if total score is > 21 then treat ace as 1 and subtract 10 for each ace
    public int getValue(ArrayList<String> hand){
        int total = 0; // total score to return
        int aceCount = 0; // counter for Ace cards

        for (String s : hand) { // iterate through a hand

            if (s.equals("A")) { // if ace, increment aceCount and add 11 to total
                total += 11;
                aceCount++;
            } else if (s.equals("J") || s.equals("Q") || s.equals("K")) { // if face card, add 10
                total += 10;
            } else {
                total += Integer.parseInt(s); // else parse card string for int and sum
            }
        }
        while (total > 21 && aceCount > 0) { // if hand has aces and hand > 21, subtract 10 till no aces left or total <=21
            total -= 10;
            aceCount--;
        }
        return total; // return total hand score
    }

    // helper method for displaying the value calculated above,
    // Parameter -  ArrayList<String> hand - array of cards as string
    // If player has an ace, handles correct display of a potential "low/high" card total (2 values)
    public void displayValue(ArrayList<String> hand){

        int high = getValue(hand); // get high value (aces as 11)
        int low = 0; // low value (treat ace as 1)
        for (String s : hand) { // iterate through hand

            if (s.equals("A")) { // if ace increment low count
                low += 1;
            } else if (s.equals("J") || s.equals("Q") || s.equals("K")) {
                low += 10; // add 10 for face card
            } else {
                low += Integer.parseInt(s); // add regular numbered cards
            }
        }
            if(high!=low){ // if treating ace as 11 and 1 gets different totals
                System.out.println("Card value is: "+low+"/"+high); // output two values low/high
            } else {
                System.out.println("Card value is "+high); // else if same value, print normally
            }
    }

    // helper method for displaying dealers hand
    // Parameter -  ArrayList<String> hand - array of cards as string
    // keeps less print slop out of my blackjack file
    public void showOne(ArrayList<String> hand) {
        String card = hand.getFirst();
        if (card.equals("J") || card.equals("Q") || card.equals("K") || card.equals("A")) { // if face card or Ace output with numerical value for readability

            System.out.println("Dealer is showing a " + hand.getFirst() + " (10) as their first card"); // show dealer first card
        }
        else {
            System.out.println("Dealer is showing a " + hand.getFirst() + " as their first card"); // show dealer first card
        }
    }
    // same as showOne but show player hand, hiding print slop
    public void showPlayerHand(ArrayList<String> hand){
        System.out.println("Your cards: " + hand);
    }

    // same but dealer's hand (after player finishes their turn)
    public void showDealerHand(ArrayList<String> hand){
        System.out.println("Dealer's cards: "+hand);
    }
}

