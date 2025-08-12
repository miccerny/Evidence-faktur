package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) for an invoice.
 *
 * <p>Used to transfer invoice data between application layers,
 * for example, between the service layer and the user interface.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    /** Unique identifier of the invoice. */
    @JsonProperty("_id")
    private Long id;

    /** Invoice number (must not be empty). */
    @NotEmpty(message = "Invoice number must not be empty")
    private String invoiceNumber;

    /** Seller details (must be provided). */
    @NotNull(message = "Seller must be provided")
    private PersonDTO seller;

    /** Buyer details (must be provided). */
    @NotNull(message = "Buyer must be provided")
    private PersonDTO buyer;

    /** Date when the invoice was issued (must be today or in the future). */
    @FutureOrPresent
    @NotNull(message = "Issue date must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issued;

    /** Due date for payment (must be in the future). */
    @Future
    @NotNull(message = "Due date must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    /** Name of the product or service (must not be empty). */
    @NotEmpty(message = "Product name must not be empty")
    private String product;

    /** Price of the product or service (must be at least 1). */
    @Min(value = 1, message = "Price must be at least 1")
    @NotNull(message = "Price must be provided")
    private float price;

    /** VAT percentage (0–100). */
    @NotNull(message = "VAT must be provided")
    @Min(value = 0, message = "VAT must be at least 0")
    @Max(value = 100, message = "VAT must be at most 100")
    private double vat;

    /** Optional note related to the invoice. */
    private String note;
}
