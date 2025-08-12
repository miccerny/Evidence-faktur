package cz.itnetwork.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import cz.itnetwork.constant.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entita reprezentující fakturu v databázi.
 * *
 * Mapuje se na tabulku "invoice" a obsahuje údaje
 * o fakturách používané v aplikaci.
 */
@Entity(name="invoice")
@Getter
@Setter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private PersonEntity seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private PersonEntity buyer;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issued;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int vat;

    @Column(nullable = false)
    private String note;


}