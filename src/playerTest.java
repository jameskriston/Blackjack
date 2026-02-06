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


public class playerTest {
    private Hand hand;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream originalOut;


    private ArrayList<String> testHand(String... cards) {
        return new ArrayList<>(Arrays.asList(cards));
    }

    @BeforeEach
    void setup(){
        originalOut = System.out;
        System.setOut(new PrintStream(out));
        out.reset();
    }

    @AfterEach
    void teardown(){
        System.setOut(originalOut);
    }

    @Test
    void constructorTest(){
        Player player = new Player(1000);
        assertNotNull(player.hand);
        assertEquals(1000, player.balance);
        assertEquals(0,player.bet);

    }

    @Test
    void displayBalance_Test(){
        Player player = new Player(12345);
        player.displayBalance();
        String output = out.toString();
        assertTrue(output.contains("Your balance is $12345"),output);
        assertEquals(12345,player.getBalance());
    }

    @Test
    void placeBet_Test(){
        Player player = new Player(1000);
        player.placeBet(100);
        assertEquals(900,player.balance);
        assertEquals(100,player.bet);

    }
    @Test
    void doubleDown_Test(){
        Player player = new Player(1000);
        player.placeBet(100);
        player.doubleDown();
        assertEquals(800,player.balance);
        assertEquals(200,player.bet);
    }
    @Test
    void updateWinnings_Test(){
        Player player = new Player(1000);
        player.placeBet(1000);
        player.updateWinnings();
        assertEquals(2000,player.balance);
    }


}
