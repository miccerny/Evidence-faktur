package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long id;

    @Pattern(regexp = "\"^[A-Za-z0-9-]+$\"", message = "Vyplňte platné číslo faktury")
    @NotBlank(message = "Číslo faktury nesmí být prázdné")
    private String invoiceNumber;


    private PersonDTO seller;
    private PersonDTO buyer;

    @FutureOrPresent(message = "Vyberte datum aktuální nebo budoucí datum")
    private LocalDate issued;

    @Future(message = "Vyberte budoucí datum splatnosti")
    private LocalDate dueDate;

    @Pattern(regexp = "^[A-Za-z0-9._\\- ]+$", message = "Vyplňte správný název produktu")
    @NotBlank(message = "Produkt nemsí být prádzný")
    private String product;

    @Pattern(regexp = "^(\\\\d+)(\\\\.\\\\d{1,2})?$\"", message = "Vyplňte správný formát částky v číselné hodnotě")
    @NotNull(message = "Částka musí být vyplněná")
    private float price;

    @Pattern(regexp = "\"^(100|[1-9]?[0-9])$\"", message = "Vyplňte správnou hodnotu daně")
    @NotNull(message = "Hodnota nesmí být prázdná")
    private double vat;

    private String note;

}
