package cz.itnetwork.entity.filtration;

import cz.itnetwork.constant.InvoiceStatus;
import lombok.Data;

/**
 * Class used for filtering invoices based on various criteria.
 *
 * <p>Contains fields that allow specifying parameters
 * for searching and filtering invoices in the application.</p>
 */
@Data
public class InvoiceFilter {

    /** ID of the buyer to filter invoices by. */
    private Long buyerID;

    /** ID of the seller to filter invoices by. */
    private Long sellerID;

    /** Minimum invoice price to include in the results. */
    private Integer minPrice;

    /** Maximum invoice price to include in the results. */
    private Integer maxPrice;

    /** Product name or keyword to search for in invoice records. */
    private String product;

    /** Maximum number of results to return. Default is 10. */
    private Integer limit = 10;

    /** Status of the invoice to filter by (e.g., ISSUED, PAID, UNPAID, CANCELED). */
    private InvoiceStatus status;
}
