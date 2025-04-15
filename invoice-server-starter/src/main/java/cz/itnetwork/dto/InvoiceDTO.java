package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String invoiceNumber;
    private PersonDTO seller;
    private PersonDTO buyer;
    private LocalDate issued;
    private LocalDate dueDate;
    private String product;
    private float price;
    private double vat;
    private String note;

}
