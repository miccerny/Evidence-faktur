package cz.itnetwork.exceptions;


/**
 * Custom exception thrown when a user tries to register with an email that is already in use.
 * <p>
 * Extends RuntimeException and is used to signal that the email address already exists in the system.
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Email '" + email + "' je již používaný.");
    }
}
