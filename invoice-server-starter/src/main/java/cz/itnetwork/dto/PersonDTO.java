package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for a person.
 * <p>
 * Used for transferring person data between application layers,
 * for example between the service layer and the user interface.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    // Person's name; must not be empty and must have at least 3 characters
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, message = "Name must have at least 3 characters")
    private String name;

    // Identification number (IČO), must be exactly 8 digits and not empty
    @Pattern(regexp = "^\\d{8}$", message = "Number must be a valid 8-digit number")
    @NotEmpty(message = "Identification number must not be empty")
    private String identificationNumber;

    // Tax identification number (DIČ), must be exactly 10 digits and not empty
    @Pattern(regexp = "^\\d{10}$", message = "Tax number must be a valid 10-digit number")
    @NotEmpty(message = "Tax number must not be empty")
    private String taxNumber;

    // Bank account number, must not be empty
    @NotEmpty(message = "Account number must not be empty")
    private String accountNumber;

    // Bank code, must be exactly 4 digits and not empty
    @Pattern(regexp = "^\\d{4}$", message = "Invalid bank code.")
    @NotEmpty(message = "Bank code must not be empty")
    private String bankCode;

    // IBAN – must match the CZ or general IBAN format, must not be empty
    @Pattern(regexp = "^(CZ\\d{2}\\d{8,10}\\/\\d{4}|[A-Z]{2}\\d{2}[A-Z0-9]{4,30})$", message = "Invalid IBAN number.")
    @NotEmpty(message = "IBAN must not be empty")
    private String iban;

    // Telephone number in the format +countrycodeNumber, must not be empty
    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$", message = "Invalid telephone number.")
    @NotEmpty(message = "Telephone number must be provided")
    private String telephone;

    // Email address, must be valid and not empty
    @Email(message = "Please enter a valid email")
    @NotEmpty(message = "Email must not be empty")
    private String mail;

    // Street (address), must contain valid characters and must not be empty
    @Pattern(regexp = "^[A-Za-zá-žÁ-Ž0-9\\s-]+(,\\s?[0-9]+)?$", message = "Enter a valid address")
    @NotEmpty(message = "Street must be provided")
    private String street;

    // ZIP code, must match the format (e.g. 12345 or 12345-6789) and must not be empty
    @NotEmpty(message = "ZIP code must not be empty")
    @Pattern(regexp = "^\\d{4,10}(-\\d{4})?$", message = "Invalid ZIP code.")
    private String zip;

    // City, only letters, spaces and hyphens allowed, must not be empty
    @Pattern(regexp = "^[A-Za-zá-žÁ-Ž\\s-]+$", message = "Please enter a valid city name")
    @NotEmpty(message = "Please enter the city name")
    private String city;

    // Country, must be selected (not empty)
    @NotEmpty(message = "Please select a country")
    private Countries country;

    private String note;

    private String ownerEmail;
}
