package exceptions;

public class JackInTheBoxAlreadyClosedException extends RuntimeException{
    public JackInTheBoxAlreadyClosedException(String message) {
        super(message);
    }
}
