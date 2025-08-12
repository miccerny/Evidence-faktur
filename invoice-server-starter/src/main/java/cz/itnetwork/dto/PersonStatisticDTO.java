package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for transferring statistical data about persons.
 *
 * <p>Contains summary information such as the person's ID,
 * name, and total revenue associated with the person.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticDTO {

    /** Unique identifier of the person. */
    private long personID;

    /** Name of the person. */
    private String personName;

    /** Total revenue (earnings) associated with the person. */
    private BigDecimal revenue;
}
