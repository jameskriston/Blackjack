import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class handTest {
    private Hand hand;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private ArrayList<String> testHand(String... cards){
        return new ArrayList<>(Arrays.asList(cards));
    }

    @BeforeEach
    void setup() {
        hand = new Hand();
        originalOut = System.out;
        System.setOut(new PrintStream(out));
        out.reset();
    }
    @AfterEach
    void teardown(){
        System.setOut(originalOut);
    }
    @Test
    void displayValue_singleValueTest(){
        hand.displayValue(testHand("A","2","9"));
        String output = out.toString().trim();
        assertTrue(output.contains("12"),output);
        assertFalse(output.contains("/"),output);

        hand.displayValue(testHand("Q","9"));
        output = out.toString().trim();
        assertTrue(output.contains("19"),output);
        assertFalse(output.contains("/"),output);
    }
    @Test
    void displayValue_twoValuesTest(){
        hand.displayValue(testHand("A","4"));
        String output = out.toString().trim();
        assertTrue(output.contains("5/15"),output);
    }
    @Test
    void getValue_noAces() {
        assertEquals(17, hand.getValue(testHand("10", "7")));
        assertEquals(20, hand.getValue(testHand("K", "Q")));
        assertEquals(12, hand.getValue(testHand("J", "2")));
    }

    @Test
    void getValue_singleAce_soft() {
        assertEquals(16, hand.getValue(testHand("A", "5")));
        assertEquals(21, hand.getValue(testHand("A", "K")));
        assertEquals(21, hand.getValue(testHand("10", "A")));
    }
    @Test
    void getValue_singleAce_hard() {
        assertEquals(15, hand.getValue(testHand("A", "9", "5")));  // 11+9+5=25 -> 15
        assertEquals(12, hand.getValue(testHand("A", "A", "10"))); // 11+11+10=32 -> 12
    }

    @Test
    void getValue_multipleAces() {
        assertEquals(21, hand.getValue(testHand("A", "A", "9")));      // 11+1+9
        assertEquals(20, hand.getValue(testHand("A", "A", "9", "9"))); // 11+1+9+9
        assertEquals(13, hand.getValue(testHand("A", "A", "A", "10"))); // 11+1+1+10
    }

    @Test
    void showOne_Test(){
        ArrayList<String> dealerHand = testHand("5","6");
        ArrayList<String> playerHand = testHand("J","A");
        hand.showOne(dealerHand);
        String output = out.toString();
        assertTrue(output.contains("Dealer is showing a 5"),output);
        assertFalse(output.contains("6"),output);
        out.reset();
    }

    @Test
    void showDealer_Test(){
        ArrayList<String> dealerHand = testHand("10","K");
        hand.showDealerHand(dealerHand);
        String output = out.toString();
        assertTrue(output.contains("Dealer's cards:"),output);
        assertTrue(output.contains("10"),output);
        assertTrue(output.contains("K"),output);

    }

    @Test
    void showPlayer_Test(){
        ArrayList<String> playerHand = testHand("J","A");
        hand.showPlayerHand(playerHand);
        String output = out.toString();
        assertTrue(output.contains("Your cards:"),output);
        assertTrue(output.contains("J"),output);
        assertTrue(output.contains("A"),output);
    }
}





