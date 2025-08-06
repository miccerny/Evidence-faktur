package cz.itnetwork.exceptions;

/**
 * Custom exception thrown when a user with the given email is not found.
 * <p>
 * Extends RuntimeException and is used for handling authentication or user lookup errors.
 */
public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
