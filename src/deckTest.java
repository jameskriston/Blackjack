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

public class deckTest {

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
