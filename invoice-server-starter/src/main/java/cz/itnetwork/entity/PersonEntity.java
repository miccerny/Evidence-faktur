package cz.itnetwork.entity;

import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Entity representing a person in the database.
 * <p>
 * This entity is mapped to the "person" table and contains
 * basic information about a person or company used in the application.
 */
@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {

    /** Unique identifier of the person (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    private long id;

    /** Name of the person or company. */
    @Column(nullable = false)
    private String name;

    /** Unique identification number (IČO), must be unique in the database. */
    @Column(unique = true, nullable = false)
    private String identificationNumber;

    /** Tax identification number (DIČ). */
    @Column(nullable = false)
    private String taxNumber;

    /** Bank account number. */
    @Column(nullable = false)
    private String accountNumber;

    /** Bank code. */
    @Column(nullable = false)
    private String bankCode;

    /** International Bank Account Number (IBAN). */
    @Column(nullable = false)
    private String iban;

    /** Telephone number. */
    @Column(nullable = false)
    private String telephone;

    /** Email address. */
    @Column(nullable = false)
    private String mail;

    /** Street address. */
    @Column(nullable = false)
    private String street;

    /** ZIP or postal code. */
    @Column(nullable = false)
    private String zip;

    /** City name. */
    @Column(nullable = false)
    private String city;

    /** Country where the person is located. */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Countries country;

    /** Additional notes related to the person. */
    private String note;

    /** Flag indicating whether the person is hidden from listings. */
    private boolean hidden = false;

    /** List of invoices where this person is the buyer. */
    @OneToMany(mappedBy = "buyer")
    private List<InvoiceEntity> invoiceBuyer;

    /** List of invoices where this person is the seller. */
    @OneToMany(mappedBy = "seller")
    private List<InvoiceEntity> invoiceSeller;

    /** User who owns this person record. */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
}
