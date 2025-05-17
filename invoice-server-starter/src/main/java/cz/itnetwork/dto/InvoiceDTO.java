package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) pro fakturu.
 * *
 * Používá se pro přenos dat o fakturách mezi vrstvami aplikace,
 * například mezi službou a uživatelským rozhraním.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long id;

    @NotEmpty(message = "Číslo faktury nesmí být prázdné")
    private String invoiceNumber;

    @NotNull(message = "Dodavatel musí být vyplněn")
    private PersonDTO seller;
    @NotNull(message = "Odběratel musí být vyplněn")
    private PersonDTO buyer;

    @FutureOrPresent
    @NotNull(message = "Datum musí být vyplněno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issued;

    @Future
    @NotNull(message = "Datum musí být vyplněno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotEmpty(message = "Název productu nesmí být prázdný")
    private String product;

    @Min(value = 1, message = "Cena musí být alespoň 1")
    @NotNull(message = "Cena musí být vyplněna")
    private float price;

    @NotNull(message = "DPH musí být vyplněno")
    @Min(value = 0, message = "DPH musí být minimálně 0")
    @Max(value = 100, message = "DPH může být maximálně 100")
    private double vat;


    private String note;

}
