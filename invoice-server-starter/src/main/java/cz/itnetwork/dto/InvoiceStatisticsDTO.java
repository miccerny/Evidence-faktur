package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO class for transferring statistical data about invoices.
 * <p>
 * Contains summary information, such as
 * total amounts or the number of invoices.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {

    private BigDecimal currentYearSum;
    private BigDecimal allTimeSum;
    private long invoicesCount;

}
