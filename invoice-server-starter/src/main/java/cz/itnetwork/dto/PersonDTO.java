/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) pro osobu.
 * *
 * Používá se pro přenos dat o osobě mezi vrstvami aplikace,
 * například mezi službou a uživatelským rozhraním.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    // Jméno osoby, nesmí být prázdné a musí mít alespoň 3 znaky
    @NotEmpty(message = "Jméno nesmí být prázdné")
    @Size(min = 3, message = "Jméno musí mít alespoň 3 znaky")
    private String name;

    // IČO (identifikační číslo osoby), musí být přesně 8 číslic a nesmí být prázdné
    @Pattern(regexp = "^\\d{8}$", message = "Číslo musí být platné 8-místné")
    @NotEmpty(message = "IČ nesmí být prázdné")
    private String identificationNumber;

    // DIČ (daňové identifikační číslo), musí být přesně 10 číslic a nesmí být prázdné
    @Pattern(regexp = "^\\d{10}$", message = "Číslo DIČ musí být platné 10ti-místné")
    @NotEmpty(message = "Nesmí být prázdné")
    private String taxNumber;

    // Číslo bankovního účtu, nesmí být prázdné
    @NotEmpty(message = "Číslo účtu nesmí být prázdné")
    private String accountNumber;

    // Kód banky, musí být přesně 4 číslice a nesmí být prázdný
    @Pattern(regexp = "^\\d{4}$", message = "Neplatný kód banky.")
    @NotEmpty(message = "Kód banky nesmí být prázdný")
    private String bankCode;

    // IBAN – musí odpovídat formátu CZ nebo obecně IBAN, nesmí být prázdný
    @Pattern(regexp = "^(CZ\\d{2}\\d{8,10}\\/\\d{4}|[A-Z]{2}\\d{2}[A-Z0-9]{4,30})$", message = "Neplatné číslo účtu.")
    @NotEmpty(message = "IBAN nesmí být prázdný")
    private String iban;

    // Telefonní číslo ve formátu +kódzeměčíslo, nesmí být prázdné
    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$", message = "Neplatné telefonní číslo.")
    @NotEmpty(message = "Telefonní číslo musí být vyplněné")
    private String telephone;

    // Emailová adresa, musí být validní a nesmí být prázdná
    @Email(message = "Vyplňte platný email")
    @NotEmpty(message = "Email nesmí být prázdný")
    private String mail;

    // Ulice (adresa), musí obsahovat platné znaky a nesmí být prázdná
    @Pattern(regexp = "^[A-Za-zá-žÁ-Ž0-9\\s-]+(,\\s?[0-9]+)?$", message = "Zadejte platnou adresu")
    @NotEmpty(message = "Ulice musí být vyplněná")
    private String street;

    // PSČ, musí odpovídat formátu (např. 12345 nebo 12345-6789) a nesmí být prázdné
    @NotEmpty(message = "PSČ nesmí být prázdné")
    @Pattern(regexp = "^\\d{4,10}(-\\d{4})?$", message = "Neplatné PSČ.")
    private String zip;

    // Město, jen písmena, mezery a pomlčky, nesmí být prázdné
    @Pattern(regexp = "^[A-Za-zá-žÁ-Ž\\s-]+$", message = "Vyplňte platné město")
    @NotEmpty(message = "Prosím vyplňte název města")
    private String city;

    // Země, musí být vybraná (není prázdná)
    @NotEmpty(message = "Vyberte název země")
    private Countries country;

    private String note;

}
