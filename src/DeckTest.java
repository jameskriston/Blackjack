import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private ArrayList<String> deck;

    @BeforeEach
    void setup(){
        deck = Deck.makeDeck();
    }
    @Test
    void makeDeck_sizeTest(){
        assertEquals(312,deck.size());
    }
    @Test
    void dealCard_Test(){
        int size_before = deck.size();
        String card = Deck.dealCard(deck);
        assertNotNull(card);
        assertEquals(size_before-1,deck.size());
        ArrayList<String> list = new ArrayList<>(Arrays.asList("A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K"));
        assertTrue(list.contains(card));

    }

}
