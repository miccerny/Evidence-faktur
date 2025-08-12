package cz.itnetwork.exceptions;

/**
 * Exception thrown when a user with the specified email address cannot be found.
 */
public class EmailNotFoundException extends RuntimeException {

    /**
     * Creates a new EmailNotFoundException with the given message.
     *
     * @param message the detail message describing the error
     */
    public EmailNotFoundException(String message) {
        super(message);
    }
}
