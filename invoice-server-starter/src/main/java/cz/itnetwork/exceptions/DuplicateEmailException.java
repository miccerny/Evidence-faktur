package cz.itnetwork.exceptions;


/**
 * Exception thrown when an attempt is made to register or create a user
 * with an email address that is already in use.
 */
public class DuplicateEmailException extends RuntimeException {

    /**
     * Creates a new DuplicateEmailException with a message indicating the email is already taken.
     *
     * @param email the email address that caused the conflict
     */
    public DuplicateEmailException(String email) {
        super("Email '" + email + "' is already in use.");
    }
}
