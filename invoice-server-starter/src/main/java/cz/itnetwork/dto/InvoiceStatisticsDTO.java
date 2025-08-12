package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for transferring invoice statistics.
 *
 * <p>Contains aggregated information such as the total amounts
 * and the total number of invoices.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {

    /** Total amount of invoices for the current year. */
    private BigDecimal currentYearSum;

    /** Total amount of invoices for all time. */
    private BigDecimal allTimeSum;

    /** Total number of invoices. */
    private long invoicesCount;
}
