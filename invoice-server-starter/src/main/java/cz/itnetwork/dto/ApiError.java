package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Class representing the structure of an API error response.
 *
 * Used to send error information to the client.
 * Contains the HTTP status code, error message, and the timestamp when the error occurred.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    /** HTTP status code of the error, e.g., 404 or 500 */
    private int status;

    /** Description of the error message */
    private String message;

    /** Time when the error occurred, in ISO format (String) */
    private String timestamp;

    /**
     * Constructor setting status and message,
     * and automatically sets the current time as the timestamp.
     *
     * @param status HTTP status code
     * @param message error message
     */
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
