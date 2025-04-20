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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    @NotEmpty(message = "Jméno nesmí být prázdné")
    @Size(min = 3, message = "Jméno musí mít alespoň 3 znaky")
    private String name;

    @NotEmpty(message = "IČ nesmí být prázdné")
    private String identificationNumber;

    @NotEmpty(message = "Nesmí být prázdné")
    private String taxNumber;

    @NotEmpty(message = "Číslo účtu nesmí být prázdné")
    private String accountNumber;

    @Pattern(regexp = "^\\d{4}$", message = "Neplatný kód banky.")
    @NotEmpty(message = "Kód banky nesmí být prázdný")
    private String bankCode;

    @Pattern(regexp = "^(CZ\\d{2}\\d{8,10}\\/\\d{4}|[A-Z]{2}\\d{2}[A-Z0-9]{4,30})$", message = "Neplatné číslo účtu.")
    @NotEmpty(message = "IBAN nesmí být prázdný")
    private String iban;

    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$", message = "Neplatné telefonní číslo.")
    @NotEmpty(message = "Telefonní číslo musí být vyplněné")
    private String telephone;

    @Email
    @NotEmpty(message = "Email nesmí být prázdný")
    private String mail;

    @NotEmpty(message = "Ulice musí být vyplněná")
    private String street;

    @NotEmpty(message = "PSČ nesmí být prázdné")
    @Pattern(regexp = "^\\d{4,10}(-\\d{4})?$", message = "Neplatné PSČ.")
    private String zip;

    @NotEmpty(message = "Prosím vyplňte název města")
    private String city;

    @NotEmpty(message = "Vyberte název země")
    private Countries country;

    private String note;

}
