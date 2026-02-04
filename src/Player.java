import java.util.ArrayList;

public class Player {

    public ArrayList<String> hand;
    public int bet;
    public int balance;

    public Player(int startBalance){
        hand = new ArrayList<String>();
        balance = startBalance;
        bet = 0;

    }

    public int getBalance(){
        return balance;
    }

    public void displayBalance(){
        System.out.println("Your balance is $"+balance);
    }
    public void placeBet(int num){
        bet = num;
        balance -= num;
    }
    public void doubleDown(){
        balance -= bet;
        bet *= 2;
    }

    public void updateWinnings(){
        balance += 2*bet;
    }
}
