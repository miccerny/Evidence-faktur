package cz.itnetwork.exceptions;

/**
 * Custom exception thrown when a user provides an incorrect password.
 * <p>
 * Extends RuntimeException and is used for authentication error handling.
 */
public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String message) {
        super(message);
    }
}
