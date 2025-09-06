import exceptions.JackInTheBoxAlreadyClosedException;
import exceptions.JackInTheBoxCannotCrankException;

public class JackInTheBox {

    private int cranks;

    private boolean isOpen;

    private RandomWrapper randomWrapper;

    public JackInTheBox() {
      this(new RandomWrapper());
    }

    public JackInTheBox(RandomWrapper randomWrapper){
        this.randomWrapper = randomWrapper;
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
           if (canOpen()){
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

    private boolean canOpen(){
        int random = randomWrapper.nextInt(10);
        System.out.println(random);
        return random % 2 == 0;
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

    protected RandomWrapper getRandomWrapper() {
        return this.randomWrapper;
    }
}
