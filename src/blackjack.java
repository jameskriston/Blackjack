import java.util.ArrayList;

import java.util.Scanner;

// I hope nobody has to read this code
public class blackjack {
    public static Scanner scan = new Scanner(System.in);
    public static Player player = new Player(1000);
    public static ArrayList<String> dealerHand = new ArrayList<>();
    public static ArrayList<String> deck = Deck.makeDeck();
    public static Hand HandHelper = new Hand();
    public static boolean runGame = true;

     public static void main(String[] args) {

        System.out.println("Would you like to play blackjack? (Type Y to play, N to exit)"); // prompt user

        while (player.getBalance()>=1 && runGame) { // while player balance has at least 1 dollar and runGame flag true

            String input = scan.nextLine(); // scan for input, trimming excess whitespace

            if ("y".equalsIgnoreCase(input)) { // if yes, setup bet
                setupBet();
            } else if ("n".equalsIgnoreCase(input)) { // if no, exit with setting runGame flag false
                System.out.println("You entered No, exiting blackjack program.");
                runGame = false;
            } else {
                System.out.println("Enter Y or N"); // if bad input prompt user again
            }
        }
     }
     // setupBet method handles user bet input
    public static void setupBet() {

        if (player.getBalance()<1) { // if user is broke, return to exit game
            System.out.println("You have $0 left, no more bets can be placed.");
            runGame = false;
            return;
        }
            player.displayBalance(); // display balance,and prompt user for bet
            System.out.println("Enter your bet amount as a whole dollar amount or N to exit");

            String inputError = "You must enter a whole dollar amount of at least 1, and no more than " + player.getBalance()+"\nEnter bet again"; // error message for bad input

            while (runGame) {
                int num; // int to hold input for bet
                String input = scan.nextLine().trim();
                if(input.equalsIgnoreCase("n")){
                    runGame = false;
                    return;
                }
                try { // parseInt can cause exceptions in many cases, wrap in try/catch blocks to handle errors
                    num = Integer.parseInt(input);
                }
                catch (NumberFormatException e) {
                    System.out.println(inputError); // if bad input output error msg
                    continue;
                }
                if (num < 1 || num > player.getBalance()) { // valid integer, but either negative or > balance
                    System.out.println(inputError); // give error message
                    continue;
                }
                     System.out.println("You bet $"+num);
                     player.placeBet(num); // handle correctly inputted player bet
                     dealCards(); // begin first card dealing
                     return;
            }
        }

    // Deals first round of cards, alternating cards between player and dealer
    public static void dealCards() {
        deck = Deck.makeDeck(); // make a deck (6 decks * 52 cards/deck)
        // deal initial 2 cards to player and dealer
        player.hand.add(Deck.dealCard(deck));
        dealerHand.add(Deck.dealCard(deck));
        player.hand.add(Deck.dealCard(deck));
        dealerHand.add(Deck.dealCard(deck));
        int playerValue = HandHelper.getValue(player.hand);
        displayHelper(true);
        HandHelper.showOne(dealerHand); // show dealer's first card

        if(playerValue==21){ // if player has 21 from first deal
            endGame(); // go to endGame method to finish dealer turn and determine winner
            return;
        }
        gameLoop(); // go to gameLoop if player has cards with score < 21
    }

    // main loop to handle gameplay loop of hit/stand/double
    public static void gameLoop() {

        while (runGame) {

           boolean doubleFlag = player.getBalance()>=player.getBet();
           // player can only double down on first hit and if they have enough balance to double their already placed bet
           System.out.println("Please enter "+(doubleFlag ? "H/S/D to Hit/Stand/Double Down" : "H/S to Hit/Stand")); // prompt user based on flag above
           String input = scan.nextLine().trim();

            if ("h".equalsIgnoreCase(input)) { // h or H input - deal 1 card to player
                player.hand.add(Deck.dealCard(deck)); // deal card
                int playerValue = HandHelper.getValue(player.hand); // get total score of player's cards
                displayHelper(true);

                if (playerValue >= 21) { // if player busts or blackjack go to endGame()
                    endGame(); // if player busts or has 21, player cannot hit make more moves go to endgame sequence
                    resetGame();// after returning from endgame go to resetGame
                    return;
                }
                continue;
            }

            if ("s".equalsIgnoreCase(input)) {
                endGame(); // endgame sequence if player stands
                resetGame();// after returning from endgame go to resetGame
                return;
            }
            if ("d".equalsIgnoreCase(input) && (doubleFlag)) { // if player can double down
                player.doubleDown(); // correct player balance+bet
                player.hand.add((Deck.dealCard(deck))); //deal last card
                System.out.println("Doubling down! Your last card is a "+player.hand.getLast());
                displayHelper(true);
                endGame(); // go to endgame sequence
                resetGame(); // after returning from endgame go to resetGame
                return;
            }
            System.out.println("Invalid input"); // if bad input given let user know
            }
        }

        // method endGame() finishes dealer's turn, determines win/loss/draw
    public static void endGame() {
        int playerValue = HandHelper.getValue(player.hand); // total score player's hands
        int dealerValue = HandHelper.getValue(dealerHand); // total score dealer's hand

        if(playerValue > 21) { // if player score > 21 they bust
            System.out.println("You busted.");
            System.out.println("Dealer revealing second card"); // display dealer cards then return
            displayHelper(false);
            return;
        }

        System.out.println("Dealer revealing second card"); // if no player bust, reveal dealer 2nd card
        displayHelper(false);

        if(dealerValue<17) { // if dealer score < 17 dealer must hit, update scores/display
            System.out.println("Dealer hitting...");
            while (dealerValue < 17) {
                dealerHand.add(Deck.dealCard(deck));
                dealerValue = HandHelper.getValue(dealerHand);
            }
        displayHelper(false);
        }

        if (dealerValue > 21) {
            System.out.println("Dealer busts. You win $" +2*player.getBet()); // if dealer busts player wins
            player.win(); // updates player balance
        }
        else if(dealerValue > playerValue) { // if dealer wins, player wins nothing bet is lost
            System.out.println("Dealer wins");
        }
        else if(playerValue > dealerValue) { // if neither player/dealer bust, but player > dealer
            System.out.println("You win $"+ 2*player.getBet()); // player wins 2 * bet
            player.win(); // update player balance
        } else {
                System.out.println("Push/Draw"); // else both scores must be equal
                player.push(); // player gets their original bet returned
            }
    }
    // handle resetting game after winner decided
    public static void resetGame() {
        // reset all necessary values, clearing hands and deck
        player.bet = 0;
        dealerHand.clear();
        player.hand.clear();
        deck.clear();
        System.out.println("Would you like to play again? (Type Y to play, N to exit)"); // prompt user

        while (runGame) {
            String input = scan.nextLine().trim();
            
            if ("y".equalsIgnoreCase(input)) { // if user wants to play again back to setupBet()
                System.out.println("You entered Yes, setting up game...");
                setupBet();
                return;
            } else if ("n".equalsIgnoreCase(input)) { // else if No, set runGame flag to false and return
                System.out.println("You entered No, exiting blackjack program.");
                runGame = false;
                return;
            }
            else {
                System.out.println("Please enter Y or N"); // if bad input prompt user again
            }
        }
    }

        // helper method to reduce repetition - takes boolean param, 1=player, 0=dealer
        // calls HandHelper methods which uses various print statements
        public static void displayHelper(boolean flag){
            if(flag){
                HandHelper.showPlayerHand(player.hand);
                HandHelper.displayValue(player.hand);
            }
            else {
                HandHelper.showDealerHand(dealerHand);
                HandHelper.displayValue(dealerHand);
            }
        }
}
