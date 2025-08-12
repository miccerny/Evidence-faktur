package cz.itnetwork.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import cz.itnetwork.constant.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing an invoice in the database.
 * <p>
 * This entity is mapped to the "invoice" table and contains
 * invoice-related data used in the application.
 */
@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {

    /** Unique identifier of the invoice (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /** Invoice number, cannot be null. */
    @Column(nullable = false)
    private String invoiceNumber;

    /** Seller associated with this invoice (many-to-one relationship). */
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private PersonEntity seller;

    /** Buyer associated with this invoice (many-to-one relationship). */
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private PersonEntity buyer;

    /** Date when the invoice was issued. */
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issued;

    /** Due date of the invoice. */
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    /** Product or service described in the invoice. */
    @Column(nullable = false)
    private String product;

    /** Price of the invoice (excluding VAT). */
    @Column(nullable = false)
    private BigDecimal price;

    /** VAT rate applied to the invoice (in percentage). */
    @Column(nullable = false)
    private int vat;

    /** Additional notes or comments related to the invoice. */
    @Column(nullable = false)
    private String note;
}