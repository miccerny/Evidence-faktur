package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface defining operations for invoice management.
 * <p>
 * Includes methods for creating, updating, deleting,
 * and retrieving invoices.
 */
public interface InvoiceService {

    /**
     * Creates a new invoice based on the provided data.
     *
     * @param invoiceDTO the data of the invoice to be created
     * @return the created invoice as an InvoiceDTO
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     * Returns a paginated list of invoices based on the given filters.
     *
     * @param invoiceFilter criteria for filtering invoices
     * @param pageable pagination information (page number, page size, sorting)
     * @return a page of invoices matching the filters
     */
    Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable);

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id the unique identifier of the invoice
     * @return the invoice as an InvoiceDTO, if found
     */
    InvoiceDTO getInvoice(Long id);

    /**
     * Retrieves all sales (invoices) where the seller has the specified identification number.
     *
     * @param identificationNumber the seller's identification number
     * @return a list of invoices for the given seller identification number
     */
    List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber);

    /**
     * Retrieves all purchases (invoices) where the buyer has the specified identification number.
     *
     * @param identificationNumber the buyer's identification number
     * @return a list of invoices for the given buyer identification number
     */
    List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber);

    /**
     * Updates an existing invoice by its ID using the provided data.
     *
     * @param id the ID of the invoice to be updated
     * @param invoiceDTO the new data for the invoice
     * @return the updated invoice as an InvoiceDTO
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     * Deletes an invoice by its ID.
     *
     * @param id the ID of the invoice to be deleted
     */
    void remove(Long id);

    /**
     * Retrieves statistics related to invoices, such as the total number of invoices,
     * total amount, and other aggregated data.
     *
     * @return an InvoiceStatisticsDTO containing invoice statistics
     */
    InvoiceStatisticsDTO getStatistics();
}
