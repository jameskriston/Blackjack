import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class handTest {
    private Hand hand;

    @BeforeEach
    void setup() {
        hand = new Hand();
    }

    private ArrayList<String> h(String... cards) {
        return new ArrayList<>(Arrays.asList(cards));
    }

    @Test
    void getValue_noAces() {
        assertEquals(17, hand.getValue(h("10", "7")));
        assertEquals(20, hand.getValue(h("K", "Q")));
        assertEquals(12, hand.getValue(h("J", "2")));
    }

    @Test
    void getValue_singleAce_soft() {
        assertEquals(16, hand.getValue(h("A", "5")));
        assertEquals(21, hand.getValue(h("A", "K")));
        assertEquals(21, hand.getValue(h("10", "A")));
    }

    @Test
    void getValue_singleAce_mustDowngrade() {
        assertEquals(15, hand.getValue(h("A", "9", "5")));  // 11+9+5=25 -> 15
        assertEquals(12, hand.getValue(h("A", "A", "10"))); // 11+11+10=32 -> 12
    }

    @Test
    void getValue_multipleAces() {
        assertEquals(21, hand.getValue(h("A", "A", "9")));      // 11+1+9
        assertEquals(20, hand.getValue(h("A", "A", "9", "9"))); // 11+1+9+9
        assertEquals(13, hand.getValue(h("A", "A", "A", "10"))); // 11+1+1+10
    }

    @Test
    void displayValue_printsLowHigh_whenDifferent() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            hand.displayValue(h("A", "5")); // should print 6/16
        } finally {
            System.setOut(old);
        }
        String printed = out.toString().trim();
        assertTrue(printed.contains("6/16"), "Expected output to contain '6/16' but got: " + printed);
    }

    @Test
    void displayValue_printsSingle_whenSame() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(out));
        try {
            hand.displayValue(h("A", "9", "5")); // should print just 15
        } finally {
            System.setOut(old);
        }
        String printed = out.toString().trim();
        assertTrue(printed.contains("15"), "Expected output to contain '15' but got: " + printed);
        assertFalse(printed.contains("/"), "Did not expect two values, got: " + printed);
    }
}




}
