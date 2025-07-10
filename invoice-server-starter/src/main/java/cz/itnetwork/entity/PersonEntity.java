package cz.itnetwork.entity;

import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Entita reprezentující osobu v databázi.
 * *
 * Mapuje se na tabulku "person" a obsahuje základní
 * informace o osobě, které se používají v aplikaci.
 */
@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String identificationNumber;

    @Column(nullable = false)
    private String taxNumber;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Countries country;

    private String note;

    private boolean hidden = false;

    @OneToMany(mappedBy = "buyer")
    private List<InvoiceEntity> invoiceBuyer;

    @OneToMany(mappedBy = "seller")
    private List<InvoiceEntity> invoiceSeller;
}
