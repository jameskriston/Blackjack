import java.util.ArrayList;
public class Hand {

    public int getValue(ArrayList<String> hand){
        int total = 0;
        int aceCount = 0;

        for (String s : hand) {

            if (s.equals("A")) {
                total += 11;
                aceCount++;
            } else if (s.equals("J") || s.equals("Q") || s.equals("K")) {
                total += 10;
            } else {
                total += Integer.parseInt(s);
            }
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;

    }

    public void displayValue(ArrayList<String> hand){

        int high = getValue(hand);
        int low = 0;
        for (String s : hand) {

            if (s.equals("A")) {
                low += 1;

            } else if (s.equals("J") || s.equals("Q") || s.equals("K")) {
                low += 10;
            } else {
                low += Integer.parseInt(s);
            }
        }
            if(high!=low){
                System.out.println("Card value is: "+low+"/"+high);
            }
            else{
                System.out.println("Card value is "+high);
            }
    }

    public void showOne(ArrayList<String> hand){
        System.out.println("Dealer is showing a "+hand.getFirst());

    }

    public void showPlayerHand(ArrayList<String> hand){
        System.out.println("Your cards: " + hand);
    }

    public void showDealerHand(ArrayList<String> hand){
        System.out.println("Dealer's cards: "+hand);
    }
}

