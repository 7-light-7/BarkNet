package alpha.net.exception;


/**
 * Custom exception for pet-related errors. Used to propagate errors from the service layer to the controller layer
 * and to provide a consistent error message format.
 */
public class PetException extends Exception {

    /**
     * Constructs a new PetException with the specified detail message.
     * 
     * @param message the detail message
     */
    public PetException(String message) {
        super(message);
    }

    /**
     * Constructs a new PetException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public PetException(String message, Throwable cause) {
        super(message, cause);
    }
}
