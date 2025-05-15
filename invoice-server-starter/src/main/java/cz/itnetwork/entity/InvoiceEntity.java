package cz.itnetwork.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 */
@Entity(name="invoice")
@Getter
@Setter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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