package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a user.
 *
 * <p>Used for transferring user-related data between application layers,
 * for example between the API layer and the service layer.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /** Unique identifier of the user. */
    @JsonProperty("_id")
    private Long userId;

    /** Email address of the user. Must be in a valid email format. */
    @Email
    private String email;

    /**
     * User's password.
     * <p>Cannot be blank and must contain at least 6 characters.</p>
     */
    @NotBlank(message = "Please provide a user password")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    /** Indicates whether the user has administrative privileges. */
    @JsonProperty("isAdmin")
    private boolean admin;

    /** List of granted authorities/roles assigned to the user. */
    private List<String> authorities;
}
