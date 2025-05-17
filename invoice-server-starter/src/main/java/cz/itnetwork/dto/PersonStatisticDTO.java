package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO třída pro přenos statistických údajů o osobách.
 * *
 * Obsahuje souhrnné informace jako ID osoby, jméno a
 * celkový příjem (tržby) spojené s danou osobou.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticDTO {

    private long personID;
    private String personName;
    private BigDecimal revenue;
}
