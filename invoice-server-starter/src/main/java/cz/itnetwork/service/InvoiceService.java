package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface defining operations for managing invoices.
 * <p>
 * Includes methods for creating, updating, deleting,
 * and retrieving invoices.
 */
public interface InvoiceService {

    /**
     * Creates a new invoice based on the provided data.
     *
     * @param invoiceDTO the invoice data to create
     * @return the created invoice as InvoiceDTO
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     * Returns a paged list of invoices according to the given filters.
     *
     * @param invoiceFilter criteria for filtering invoices
     * @param pageable information about pagination (page, size, sorting)
     * @param userEntity the user requesting the invoices
     * @return a page of invoices matching the filters
     */
    Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable);

    /**
     * Finds an invoice by its ID.
     *
     * @param id the invoice identifier
     * @return the invoice as InvoiceDTO, if it exists
     */
    InvoiceDTO getInvoice(Long id);

    /**
     * Returns all sales (invoices) where the seller has the given identification number.
     *
     * @param identificationNumber the seller's identification number
     * @return a list of invoices for the given seller identification number
     */
    List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber);

    /**
     * Returns all purchases (invoices) where the buyer has the given identification number.
     *
     * @param identificationNumber the buyer's identification number
     * @return a list of invoices for the given buyer identification number
     */
    List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber);

    /**
     * Updates an existing invoice by its ID and the new data.
     *
     * @param id the ID of the invoice to update
     * @param invoiceDTO the new invoice data
     * @return the updated invoice as InvoiceDTO
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     * Deletes an invoice by its ID.
     *
     * @param id the ID of the invoice to delete
     */
    void remove(Long id);

    /**
     * Returns invoice statistics (such as summary data like the number of invoices, total prices, etc.).
     *
     * @return an object with invoice statistics
     */
    InvoiceStatisticsDTO getStatistics();
}
