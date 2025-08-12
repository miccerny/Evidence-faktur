package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Class representing the structure of an API error response.
 *
 * <p>Used to send error details to the client.
 * Contains the HTTP status code, an error message, and the timestamp
 * of when the error occurred.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    /** HTTP status code of the error, e.g., 404 or 500. */
    private int status;

    /** Description of the error message. */
    private String message;

    /** Time when the error occurred, in ISO format (String). */
    private String timestamp;

    /**
     * Constructs an {@code ApiError} with the given status and message,
     * automatically setting the current timestamp.
     *
     * @param status  HTTP status code
     * @param message error message
     */
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
