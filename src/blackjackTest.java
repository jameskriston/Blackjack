import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class blackjackTest {

private InputStream oldInput;
private PrintStream oldOutput;
private final ByteArrayOutputStream out = new ByteArrayOutputStream();


    @BeforeEach
    void setup() {

        oldInput = System.in;
        oldOutput = System.out;
        System.setOut(new PrintStream(out));
        out.reset();

        blackjack.playGame = true;
        blackjack.scan = new Scanner(System.in);
        blackjack.player = new Player(1000);
        blackjack.dealerHand = new ArrayList<>();
        blackjack.deck = Deck.makeDeck();
        blackjack.Hand = new Hand();
        blackjack.doubleFlag = true;
    }

    @AfterEach
    void teardown(){

        System.setIn(oldInput);
        System.setOut(oldOutput);
    }

    private void giveInput(String str){

        System.setIn(new ByteArrayInputStream(str.getBytes()));
        blackjack.scan = new Scanner(System.in);

    }

    @Test
    void EmptyBalanceTest(){
        blackjack.player.balance = 0;
        blackjack.setupBet();
        assertFalse(blackjack.playGame, "Expected false if balance 0");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You have $0 left, no more bets can be placed."), sysOut);
    }

    @Test
    void dealerBustTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("10");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("5");
        blackjack.dealerHand.add("K");

        giveInput("n\n");
        blackjack.endGame();

        assertEquals(300, blackjack.player.balance, "Dealer bust, player wins, balance expected = 300");
        assertFalse(blackjack.playGame, "N is given at end of game to return/exit, expected false");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Dealer busts"),sysOut);


    }


    @Test
    void dealerWinTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("6");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("5");
        blackjack.dealerHand.add("6");

        giveInput("n\n");
        blackjack.endGame();

        assertEquals(100, blackjack.player.balance, "Dealer win, player loses, balance expected = 100");
        assertFalse(blackjack.playGame, "N is given at end of game to return/exit, expected false");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Dealer wins"),sysOut);

    }

    @Test
    void playerBustTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("7");
        blackjack.player.hand.add("7");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("5");
        blackjack.dealerHand.add("5");

        giveInput("n\n");

        blackjack.endGame();
        assertEquals(100, blackjack.player.balance, "Player busts, balance expected = 100");
        assertFalse(blackjack.playGame, "N is given at end of game to return/exit, expected false");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You busted"),sysOut);


    }

    @Test
    void playerBlackJackTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("A");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("5");
        blackjack.dealerHand.add("5");

        giveInput("n\n");

        blackjack.endGame();
        assertEquals(300, blackjack.player.balance, "Player wins with blackjack (21), balance expected = 300");
        assertFalse(blackjack.playGame, "N is given at end of game to return/exit, expected false");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You got Blackjack"),sysOut);


    }

    @Test
    void drawTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("8");


        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("8");

        giveInput("n\n");

        blackjack.endGame();
        assertEquals(200, blackjack.player.balance, "Player busts, balance expected = 200");
        assertFalse(blackjack.playGame, "N is given at end of game to return/exit, expected false");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Draw"),sysOut);

    }

    @Test
    void replayTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;


        giveInput("y\nx\n");

        blackjack.resetGame();
        assertTrue(blackjack.playGame, "Y is given at end of game to play again, expected true");
        assertEquals(0, blackjack.player.bet, "Bet should be zero after exit");
        assertEquals(0, blackjack.player.hand.size(), "Player should have no cards upon exit");

        String sysOut = out.toString();
        assertTrue(sysOut.contains("Would you like to play again"),sysOut);
        assertTrue(sysOut.contains("Enter your bet"),sysOut);
    }
    @Test
    void badBetAlphabetTest(){


        giveInput("abc\nx\n");
        blackjack.setupBet();
        assertEquals(0, blackjack.player.bet, "Bet should be zeor after exit");

        String sysOut = out.toString();


        assertTrue(sysOut.contains("You must enter an integer"), "Enter bad bet amount");
        assertFalse(blackjack.playGame);
    }

    @Test
    void badBetNumTest(){


        giveInput("-1\nx\n");
        blackjack.setupBet();
        assertEquals(0, blackjack.player.bet, "Bet should be zero after exit");

        String sysOut = out.toString();


        assertTrue(sysOut.contains("You must enter an integer"), "Enter bad bet amount");
        assertFalse(blackjack.playGame);
    }

}
