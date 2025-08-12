package cz.itnetwork.exceptions;


public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Email '" + email + "' je již používán.");
    }
}
