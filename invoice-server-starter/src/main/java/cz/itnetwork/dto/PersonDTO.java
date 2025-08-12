package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for a person.
 *
 * <p>Used to transfer person-related data between application layers,
 * such as between the service layer and the user interface.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    /** Person's name. Must not be empty and must contain at least 3 characters. */
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, message = "Name must have at least 3 characters")
    private String name;

    /** Identification number (IČO) – must be exactly 8 digits and not empty. */
    @Pattern(regexp = "^\\d{8}$", message = "Identification number must be a valid 8-digit number")
    @NotEmpty(message = "Identification number must not be empty")
    private String identificationNumber;

    /** Tax identification number (DIČ) – must be exactly 10 digits and not empty. */
    @Pattern(regexp = "^\\d{10}$", message = "Tax number must be a valid 10-digit number")
    @NotEmpty(message = "Tax number must not be empty")
    private String taxNumber;

    /** Bank account number – must not be empty. */
    @NotEmpty(message = "Account number must not be empty")
    private String accountNumber;

    /** Bank code – must be exactly 4 digits and not empty. */
    @Pattern(regexp = "^\\d{4}$", message = "Invalid bank code")
    @NotEmpty(message = "Bank code must not be empty")
    private String bankCode;

    /** IBAN – must match CZ format or general IBAN format, and must not be empty. */
    @Pattern(
            regexp = "^(CZ\\d{2}\\d{8,10}\\/\\d{4}|[A-Z]{2}\\d{2}[A-Z0-9]{4,30})$",
            message = "Invalid IBAN"
    )
    @NotEmpty(message = "IBAN must not be empty")
    private String iban;

    /** Phone number – must be in the format +countryCodeNumber, and must not be empty. */
    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$", message = "Invalid phone number")
    @NotEmpty(message = "Phone number must not be empty")
    private String telephone;

    /** Email address – must be valid and not empty. */
    @Email(message = "Enter a valid email address")
    @NotEmpty(message = "Email must not be empty")
    private String mail;

    /** Street address – must contain valid characters and not be empty. */
    @Pattern(
            regexp = "^[A-Za-zá-žÁ-Ž0-9\\s-]+(,\\s?[0-9]+)?$",
            message = "Enter a valid street address"
    )
    @NotEmpty(message = "Street must not be empty")
    private String street;

    /** ZIP code – must match a valid format (e.g., 12345 or 12345-6789) and must not be empty. */
    @NotEmpty(message = "ZIP code must not be empty")
    @Pattern(regexp = "^\\d{4,10}(-\\d{4})?$", message = "Invalid ZIP code")
    private String zip;

    /** City – only letters, spaces, and hyphens allowed. Must not be empty. */
    @Pattern(regexp = "^[A-Za-zá-žÁ-Ž\\s-]+$", message = "Enter a valid city name")
    @NotEmpty(message = "City must not be empty")
    private String city;

    /** Country – must be selected (not null). */
    @NotNull(message = "Country must be selected")
    private Countries country;

    /** Additional notes about the person. */
    private String note;
}
