package cz.itnetwork.constant;

/**
 * Enumeration representing the possible statuses of an invoice.
 * <p>
 * ISSUED   - The invoice has been created and issued.
 * PAID     - The invoice has been paid.
 * UNPAID   - The invoice is not paid yet.
 * CANCELED - The invoice has been canceled.
 */
public enum InvoiceStatus {
    ISSUED,
    PAID,
    UNPAID,
    CANCELED
}
