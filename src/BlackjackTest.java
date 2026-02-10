import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

private InputStream oldInput;
private PrintStream oldOutput;
private final ByteArrayOutputStream out = new ByteArrayOutputStream();


    @BeforeEach
    void setup() {
        oldInput = System.in;
        oldOutput = System.out;
        System.setOut(new PrintStream(out));
        out.reset();
        blackjack.runGame = true;
        blackjack.scan = new Scanner(System.in);
        blackjack.player = new Player(1000);
        blackjack.dealerHand = new ArrayList<>();
        blackjack.deck = Deck.makeDeck();
        blackjack.HandHelper = new Hand();
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

    @ParameterizedTest
    @ValueSource(strings = {
            "zzz\n-1\nn\n","y\n100\nzzz\nh\ns\nn\n",
            "y\n100\nd\nn\n", "y\n999\nzzz\nd\ns\nn\n",
            "'   '\n???\nn\n", "y\n300\nh\nh\nh\nh\nn\nn\n",
            "y\n100\ns\nz\nz\n0000\n....\nh\ns\nn\n",
            "y\n100\nh\nh\nh\nh\naaaa\n1111\nn\n",
            "y\n100\nd\nd\nd\nn\n", "y\n100\nd\nn\n",
            "n\n", "34345\n!!!!\nn\n", "y\n100\nh\ns\nn\n",
            "y\n1001\n-10000\n0\nhello\n100\ns\nn\n",
            "y\n100\ns\nn\n","y\n4354350435093095\ns\nn\n","y\n100\ns\nn\n",
            "y\n100\ns\nn\n","y\n5b5\ns\nn\n","y\nbbb1000\ns\nn\n"
    })
    void randomInputTest(String input){ // test no exeptions are thrown with messy input
        giveInput(input);
        assertDoesNotThrow(()->blackjack.main((new String[]{})));
    }

    @Test
    void EmptyBalanceTest(){
        blackjack.player.balance = 0;
        blackjack.setupBet();
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

        blackjack.endGame();

        assertEquals(300, blackjack.player.balance, "Dealer bust, player wins, balance expected = 300");
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

        blackjack.endGame();

        assertEquals(100, blackjack.player.balance, "Dealer win, player loses, balance expected = 100");
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

        blackjack.endGame();
        assertEquals(100, blackjack.player.balance, "Player busts, balance expected = 100");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You busted"),sysOut);
    }

    @Test
    void player21Test(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("A");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("5");
        blackjack.dealerHand.add("5");

        blackjack.endGame();
        assertEquals(300, blackjack.player.balance, "Player wins 21, balance expected = 300");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You win"));
    }

    @Test
    void drawTest(){

        blackjack.player.bet = 100;
        blackjack.player.balance = 100;

        blackjack.player.hand.add("10");
        blackjack.player.hand.add("8");

        blackjack.dealerHand.add("10");
        blackjack.dealerHand.add("8");

        blackjack.endGame();
        assertEquals(200, blackjack.player.balance, "Player busts, balance expected = 200");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Push/Draw"),sysOut);
    }

    @Test

    void noReplayTest(){
        blackjack.player.bet = 100;
        blackjack.player.balance = 100;
        blackjack.player.hand.add("K");
        blackjack.player.hand.add("K");

        giveInput("n\n");

        blackjack.resetGame();
        assertFalse(blackjack.runGame, "Y is given at end of game to play again, expected true");
        assertEquals(0, blackjack.player.bet, "Bet should be zero after exit");
        assertEquals(0, blackjack.player.hand.size(), "Player hand should be empty when resetting");
        assertEquals(0,blackjack.dealerHand.size(), "Dealer hand should be empty when resetting");
        assertEquals(0,blackjack.deck.size(), "Deck should be cleared and size 0");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Would you like to play again"),sysOut);
    }

    @Test
    void yesReplayTest(){
        blackjack.player.bet = 100;
        blackjack.player.balance = 100;
        blackjack.player.hand.add("K");
        blackjack.player.hand.add("K");

        giveInput("y\n25\ns\nn\n");

        blackjack.resetGame();
        assertTrue(blackjack.player.balance == 75 || blackjack.player.balance == 125 || blackjack.player.balance == 100);
        assertEquals(0, blackjack.player.bet, "Bet should be zero after exit");
        assertTrue(blackjack.player.hand.isEmpty(), "After replay, hand should be empty");
        assertTrue(blackjack.dealerHand.isEmpty(), "After replay, hand should be empty");
        assertTrue(blackjack.deck.isEmpty(), "After replay of game, deck should be empty");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("Would you like to play again"),sysOut);
        assertTrue(sysOut.contains("You bet $25"),sysOut);
        assertTrue(sysOut.contains("Dealer revealing second card"));
    }

    @Test
    void badInputTest(){
        blackjack.player.balance = 500;
        giveInput("abc\n-1\n10000\n'  '\n100\ns\nn\n");
        blackjack.setupBet();
        assertEquals(0, blackjack.player.bet, "Bet should be zero after bad inputs -> good input -> end game");
        String sysOut = out.toString();
        assertTrue(sysOut.contains("You must enter a whole dollar amount"), sysOut);
        assertTrue(sysOut.contains("You bet $100"), sysOut);
    }
}
