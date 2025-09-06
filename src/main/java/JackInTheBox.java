import exceptions.JackInTheBoxAlreadyClosedException;
import exceptions.JackInTheBoxCannotCrankException;

import java.util.Random;

public class JackInTheBox {

    private int cranks;

    private boolean isOpen;

    public JackInTheBox() {
        this.cranks = 0;
        this.isOpen = false;
    }

    public void crank() {
        if (this.isOpen) {
            throw new JackInTheBoxCannotCrankException("Cannot crank when open");
        }
        this.cranks = cranks + 1;
        playNote();

        if (this.cranks >= 3) {
           if (doesOpen()){
               open();
           }
        }
    }

    private void open() {
        System.out.println("Opened!");
        this.isOpen = true;
        playFullSong();
    }

    public void close() {
        if (!isOpen) {
            throw new JackInTheBoxAlreadyClosedException("Cannot Close if already closed");
        }
        this.cranks = 0;
        this.isOpen = false;
        System.out.println("Closed");
    }

    private Boolean doesOpen(){
        Random random = new Random();
        return random.nextInt(10) % 2 == 0;
    }

    private void playNote() {
        System.out.println("\uD834\uDD1E");
    }

    private void playFullSong() {
        System.out.println("\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E\uD834\uDD1E");
    }

    public Integer getCranks() {
        return cranks;
    }

    public Boolean getOpen() {
        return isOpen;
    }

}
