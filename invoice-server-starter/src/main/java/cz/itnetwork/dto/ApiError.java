package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Třída reprezentující strukturu chybové odpovědi API
 *
 * Používá se pro zasílání informací o chybách klientovi.
 * Obsahuje HTTP status kod, zprávu a čas vzniku chyby.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    /** HTTP status kód chyby, např. 404 nebo 500 */
    private int status;

    /** Popis chybové zprávy */
    private String message;

    /** Čas, kdy chyba nastala, ve formátu ISO (String) */
    private String timestamp;

    /**
     * Konstruktor nastavující status a zprávu,
     * zároveň nastaví aktuální čas jako timestamp.
     *
     * @param status HTTP status kód
     * @param message chybová zpráva
     */
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

}
