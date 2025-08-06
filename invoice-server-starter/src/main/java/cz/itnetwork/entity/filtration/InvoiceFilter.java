package cz.itnetwork.entity.filtration;

import cz.itnetwork.constant.InvoiceStatus;
import lombok.Data;

/**
 * Class used for filtering invoices by various criteria.
 * <p>
 * Contains fields that allow you to specify parameters for searching
 * and filtering invoices in the application.
 */
@Data
public class InvoiceFilter {

    private Long buyerID;
    private Long sellerID;
    private Integer minPrice;
    private Integer maxPrice;
    private String product;
    private Integer limit=10;
    private InvoiceStatus status;

}
