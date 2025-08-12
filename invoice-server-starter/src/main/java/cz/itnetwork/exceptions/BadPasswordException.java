package cz.itnetwork.exceptions;

/**
 * Exception thrown when a user provides an incorrect password during authentication.
 * <p>
 * Extends {@link RuntimeException} so it can be thrown without being declared.
 */
public class BadPasswordException extends RuntimeException {

    /**
     * Creates a new BadPasswordException with the given detail message.
     *
     * @param message a descriptive message explaining the reason for the exception
     */
    public BadPasswordException(String message) {
        super(message);
    }
}