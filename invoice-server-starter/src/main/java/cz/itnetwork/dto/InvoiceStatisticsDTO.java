package cz.itnetwork.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO třída pro přenos statistických údajů o fakturách.
 * *
 * Obsahuje souhrnné informace, jako jsou například
 * celkové částky nebo počet faktur.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {

    private BigDecimal currentYearSum;
    private BigDecimal allTimeSum;
    private long invoicesCount;

}
