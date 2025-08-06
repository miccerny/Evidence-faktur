package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO class for transferring statistical data about persons.
 * <p>
 * Contains summary information such as the person's ID, name,
 * and the total income (sales) associated with that person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticDTO {

    private long personID;
    private String personName;
    private BigDecimal revenue;
}
