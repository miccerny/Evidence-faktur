package cz.itnetwork.constant;

/**
 * Enumeration representing the current status of an invoice.
 *
 * <p>Possible values:</p>
 * <ul>
 *     <li>{@link #ISSUED}  – The invoice has been created and issued but not yet paid.</li>
 *     <li>{@link #PAID}    – The invoice has been fully paid.</li>
 *     <li>{@link #UNPAID}  – The invoice is overdue and has not been paid.</li>
 *     <li>{@link #CANCELED} – The invoice has been canceled and is no longer valid.</li>
 * </ul>
 */
public enum InvoiceStatus {
    ISSUED, PAID, UNPAID, CANCELED
}
