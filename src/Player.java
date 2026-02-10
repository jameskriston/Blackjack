import java.util.ArrayList;

public class Player {

    public ArrayList<String> hand; // player's hand of cards
    public int bet; // players bet amount - int
    public int balance; // players balance/wallet to place bets with - int

    // setup a new player on program start taking startBalance as parameter for balance
    public Player(int startBalance){
        hand = new ArrayList<String>(); // player card hand as arraylist of strings
        balance = startBalance;
        bet = 0;
    }

    public int getBet(){ // getter method
        return bet;
    }

    public int getBalance(){ // getter method
        return balance;
    }

    public void displayBalance(){ // print formatted player balance
        System.out.println("Your balance is $"+balance);
    }

    public void win(){ // compute player balance after win (a win gives double their bet)
        balance += 2*bet;
    }

    public void push() { // compute player balance after push/draw (a push gives bet back, no loss/gain)
        balance += bet;
    }

    public void placeBet(int num){ // setter method
        bet = num;
        balance -= num;
    }

    public void doubleDown(){ // setter for if player doubles down
        balance -= bet;
        bet *= 2;
    }
}
