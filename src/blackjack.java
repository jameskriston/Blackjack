import java.util.ArrayList;

import java.util.Scanner;


// I hope nobody has to read this code

public class blackjack {
    public static Scanner scan = new Scanner(System.in);
    public static Player player = new Player(1000);
    public static ArrayList<String> dealerHand = new ArrayList<>();
    public static ArrayList<String> deck = Deck.makeDeck();
    public static Hand Hand = new Hand();
    public static boolean doubleFlag = true;

     static void main(String[] args) {

        System.out.println("Would you like to play blackjack? (Type Y to play, N to exit)");

        while (true) {
            String input = scan.nextLine();

            if ("y".equalsIgnoreCase(input)) {

                setupBet();
            } else if ("n".equalsIgnoreCase(input)) {
                System.out.println("You entered No, exiting blackjack program.");
                System.exit(0);
            }
        }
    }

    public static void setupBet() {
        if ( player.getBalance()<=0) {
            System.out.println("You have $0 left, no more bets can be placed. Exiting program.");
            System.exit(0);
        }
        int num;
        try {
            player.displayBalance();
            System.out.println("Enter your bet amount as an integer or enter X to exit.");
            while (true) {
                String input = scan.nextLine();

                if ("x".equalsIgnoreCase(input)) {
                    System.out.println("You entered X, exiting blackjack program.");
                    System.exit(0);
                }
                num = Integer.parseInt(input);
                if (num < 1 || num > player.getBalance()) {
                    System.out.println("You must enter an integer value of at least 1, and no more than " + player.getBalance());
                }
                if (num >= 1 && num <= player.getBalance()) {
                     player.placeBet(num);
                    dealCards();
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("You must enter an integer value of at least 1, and no more than " + player.getBalance());
            setupBet();
        }
    }

    public static void dealCards() {

        player.hand.add(Deck.dealCard(deck));
        dealerHand.add(Deck.dealCard(deck));
        player.hand.add(Deck.dealCard(deck));
        dealerHand.add(Deck.dealCard(deck));
        Hand.showPlayerHand(player.hand);
        Hand.displayValue(player.hand);
        Hand.showOne(dealerHand);

        if(Hand.getValue(player.hand)==21){

            System.out.println("You got Blackjack!");
            endGame();
        }
        gameLoop();
    }

    public static void gameLoop() {
        String input;
        if(player.balance< player.bet){
            doubleFlag = false;
        }
        while (true) {
        if (doubleFlag) {
            System.out.println("Would you like to Hit/Stand/Double Down? Enter H/S/D");
        } else {
            System.out.println("Would you like to Hit/Stand? Enter H/S");
        }
            input = scan.nextLine();
            if ("h".equalsIgnoreCase(input)) {
                player.hand.add(Deck.dealCard(deck));
                if(Hand.getValue((player.hand))==21){
                    Hand.showPlayerHand(player.hand);
                    Hand.displayValue(player.hand);
                    System.out.println("You got Blackjack!");
                    endGame();

                }
                if (Hand.getValue(player.hand) > 21) {
                    Hand.showPlayerHand(player.hand);
                    Hand.displayValue(player.hand);
                    System.out.println("You busted.");
                    Hand.showDealerHand(dealerHand);
                    Hand.displayValue(dealerHand);
                    resetGame();
                }
                Hand.showPlayerHand(player.hand);
                Hand.displayValue(player.hand);
                gameLoop();

            }
            if ("s".equalsIgnoreCase(input)) {
                endGame();
                break;
            }
            if ("d".equalsIgnoreCase(input) && (doubleFlag)) {

                doubleFlag = false;
                player.doubleDown();
                player.hand.add((Deck.dealCard(deck)));
                Hand.showPlayerHand(player.hand);
                Hand.displayValue(player.hand);
                endGame();
                break;
            }
        }
    }

    public static void endGame() {

        while (Hand.getValue(dealerHand) < 17) {

            dealerHand.add(Deck.dealCard(deck));


        }
        if (Hand.getValue(dealerHand) > 21) {
            Hand.showDealerHand(dealerHand);
            System.out.println("Dealer busts. You win $" + 2 * player.bet);
            player.updateWinnings();
            resetGame();
        }
            if (Hand.getValue(dealerHand) > Hand.getValue(player.hand)) {
                Hand.showDealerHand(dealerHand);
                Hand.displayValue(dealerHand);
                System.out.println("Dealer wins");

                resetGame();

            }
            else if (Hand.getValue(dealerHand) < Hand.getValue(player.hand)) {
                Hand.showDealerHand(dealerHand);
                Hand.displayValue(dealerHand);
                System.out.println("You win $"+ 2*player.bet);
                player.updateWinnings();
                resetGame();

            }
            else if (Hand.getValue(player.hand)==Hand.getValue(dealerHand)){
                Hand.showDealerHand(dealerHand);
                Hand.displayValue(dealerHand);
                System.out.println("Draw");
                player.balance+=player.bet;
                resetGame();
            }
    }

    public static void resetGame() {
        player.bet = 0;
        dealerHand.clear();
        player.hand.clear();
        doubleFlag = true;
        deck.clear();
        player.displayBalance();
        System.out.println("Would you like to play again? (Type Y to play, N to exit)");
        while (true) {
            String input = scan.nextLine();

            if ("y".equalsIgnoreCase(input)) {
                deck = Deck.makeDeck();
                setupBet();
            } else if ("n".equalsIgnoreCase(input)) {
                System.out.println("You entered No, exiting blackjack program.");
                System.exit(0);
            }
        }
    }
}
