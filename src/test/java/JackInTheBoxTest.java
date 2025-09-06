import exceptions.JackInTheBoxAlreadyClosedException;
import exceptions.JackInTheBoxCannotCrankException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JackInTheBoxTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private RandomWrapper mockRandomWrapper;

    private JackInTheBox jackInTheBox;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        mockRandomWrapper = mock(RandomWrapper.class);
        when(mockRandomWrapper.nextInt(10)).thenReturn(3).thenReturn(3).thenReturn(2).thenReturn(4);
        jackInTheBox = new JackInTheBox(mockRandomWrapper);
    }

    @Test
    void constructerCreatesRandomWrapperObject(){
        jackInTheBox = new JackInTheBox();

        assertNotNull(jackInTheBox.getRandomWrapper());
    }

    @Test
    void crank_shouldIncreaseCranks() {
        jackInTheBox.crank();

        assertEquals(1, jackInTheBox.getCranks());

        jackInTheBox.crank();

        assertEquals(2, jackInTheBox.getCranks());

        jackInTheBox.crank();

        assertEquals(3, jackInTheBox.getCranks());
    }

    @Test
    void crank_shouldOpenAfter4Times(){
        when(mockRandomWrapper.nextInt(10)).thenReturn(3).thenReturn(4);

        jackInTheBox.crank();
        jackInTheBox.crank();
        jackInTheBox.crank();

        assertEquals(false, jackInTheBox.getOpen());

        jackInTheBox.crank();

        assertEquals(true, jackInTheBox.getOpen());
    }

    @Test
    void crank_shouldReturnExceptionIfOpen(){
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
        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

        assertEquals(false, jackInTheBox.getOpen());
    }

    @Test
    void close_shouldReturnExceptionIfAlreadyClosed(){
        JackInTheBoxAlreadyClosedException exception = assertThrows(JackInTheBoxAlreadyClosedException.class, () -> {
            jackInTheBox.close();
        });

        String expectedMessage = "Cannot Close if already closed";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void open_shouldReturnTrueIfOpen() {
        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        assertEquals(true, jackInTheBox.getOpen());
    }

    @Test
    void open_shouldOpenAfterClosing(){
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
        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        jackInTheBox.close();

        assertEquals(0, jackInTheBox.getCranks());
    }

    @Test
    void playNote_shouldPlayNote(){
        jackInTheBox.crank();

        assertEquals("\uD834\uDD1E", outputStreamCaptor.toString().trim());
    }

    @Test
    void playFullSong_shouldPlayFullSong(){
        while(!jackInTheBox.getOpen()){
            jackInTheBox.crank();
        }

        String song = "\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E";
        assertTrue(outputStreamCaptor.toString().contains(song));
    }

}