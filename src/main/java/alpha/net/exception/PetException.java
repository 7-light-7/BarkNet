package alpha.net.exception;

public class PetException extends Exception {

    public PetException(String message) {
        super(message);
    }

    public PetException(String message, Throwable cause) {
        super(message, cause);
    }
}
