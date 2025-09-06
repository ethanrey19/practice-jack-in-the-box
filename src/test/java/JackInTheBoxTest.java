import exceptions.JackInTheBoxAlreadyClosedException;
import exceptions.JackInTheBoxCannotCrankException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
class JackInTheBoxTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    void crank_shouldIncreaseCranks() {
        JackInTheBox jackInTheBox = new JackInTheBox();
        jackInTheBox.crank();

        assertEquals(1, jackInTheBox.getCranks());
    }

    @Test
    void crank_shouldReturnExceptionIfOpen(){
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        JackInTheBoxCannotCrankException exception = assertThrows(JackInTheBoxCannotCrankException.class, () -> {
            jackInTheBox.crank();
        });

        String expectedMessage = "Cannot crank when open";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void close_shouldReturnFalseIfClosed() {
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

        assertEquals(false, jackInTheBox.getOpen());
    }

    @Test
    void close_shouldReturnExceptionIfAlreadyClosed(){
        JackInTheBox jackInTheBox = new JackInTheBox();

        JackInTheBoxAlreadyClosedException exception = assertThrows(JackInTheBoxAlreadyClosedException.class, () -> {
            jackInTheBox.close();
        });

        String expectedMessage = "Cannot Close if already closed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void open_shouldReturnTrueIfOpen() {
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        assertEquals(true, jackInTheBox.getOpen());
    }

    @Test
    void open_shouldOpenAfterClosing(){
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        assertEquals(true, jackInTheBox.getOpen());
    }

    @Test
    void getCranks_shouldReturn0AfterClosed(){
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

        assertEquals(0, jackInTheBox.getCranks());
    }

    @Test
    void playNote_shouldPlayNote(){
        JackInTheBox jackInTheBox = new JackInTheBox();
        jackInTheBox.crank();

        assertEquals("\uD834\uDD1E", outputStreamCaptor.toString().trim());
    }

    @Test
    void playFullSong_shouldPlayFullSong(){
        JackInTheBox jackInTheBox = new JackInTheBox();

        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        String song = "\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E";
        assertTrue(outputStreamCaptor.toString().contains(song));
    }

}