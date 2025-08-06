package cz.itnetwork.entity;

import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Entity representing a person in the database.
 * <p>
 * Maps to the "person" table and contains basic information
 * about a person used in the application.
 */
@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {

    // Primary key, unique ID for the person. Generated automatically using a database sequence.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    private long id;

    // Name of the person or company. Cannot be null.
    @Column(nullable = false)
    private String name;

    // Identification number (IČO). Must be unique and cannot be null.
    @Column(unique = true, nullable = false)
    private String identificationNumber;

    // Tax identification number (DIČ). Cannot be null.
    @Column(nullable = false)
    private String taxNumber;

    // Bank account number. Cannot be null.
    @Column(nullable = false)
    private String accountNumber;

    // Bank code (4 digits for Czech banks). Cannot be null.
    @Column(nullable = false)
    private String bankCode;

    // IBAN (International Bank Account Number). Cannot be null.
    @Column(nullable = false)
    private String iban;

    // Telephone number. Cannot be null.
    @Column(nullable = false)
    private String telephone;

    // Email address. Cannot be null.
    @Column(nullable = false)
    private String mail;

    // Street address. Cannot be null.
    @Column(nullable = false)
    private String street;

    // ZIP code. Cannot be null.
    @Column(nullable = false)
    private String zip;

    // City name. Cannot be null.
    @Column(nullable = false)
    private String city;

    // Country (e.g., CZECHIA, SLOVAKIA). Saved as a string in the database.
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Countries country;

    // Optional note or extra information about the person.
    private String note;

    // True if the person is hidden in the application (soft delete or for privacy).
    private boolean hidden = false;

    // List of invoices where this person is the buyer.
    @OneToMany(mappedBy = "buyer")
    private List<InvoiceEntity> invoiceBuyer;

    // List of invoices where this person is the seller.
    @OneToMany(mappedBy = "seller")
    private List<InvoiceEntity> invoiceSeller;

    // The user who owns this person (relationship to UserEntity).
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
}
