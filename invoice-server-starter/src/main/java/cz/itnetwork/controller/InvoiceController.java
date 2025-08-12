package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that provides API endpoints for working with invoices.
 *
 * <p>All requests related to invoices start with the path {@code /api/invoices}.
 * This controller handles operations such as retrieving, creating, updating,
 * deleting invoices, as well as retrieving invoice statistics.</p>
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    /** Injected service for handling invoice operations. */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Creates a new invoice based on the provided input data.
     *
     * @param invoiceDTO the invoice data to be created
     * @return the newly created invoice including the generated ID
     */
    @Secured("ROLE_USER")
    @PostMapping("")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.createInvoice(invoiceDTO);
    }

    /**
     * Returns a paginated list of invoices with a default page size of 5,
     * using an optional filter for narrowing results by parameters such as amount, date, etc.
     *
     * @param invoiceFilter object containing filtering parameters
     * @param pageable pagination settings (page number, page size, sorting)
     * @return a page of invoices matching the given filter
     */
    @GetMapping({"", "/"})
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, @PageableDefault(size = 5) Pageable pageable) {
        return invoiceService.getAll(invoiceFilter, pageable);
    }

    /**
     * Returns the details of a specific invoice, including seller and buyer information.
     *
     * @param invoiceId the unique ID of the invoice
     * @return the invoice details as a DTO
     */
    @GetMapping({"/{invoiceId}/", "/{invoiceId}"})
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId) {
        return invoiceService.getInvoice(invoiceId);
    }

    /**
     * Updates an existing invoice by its ID.
     *
     * @param invoiceId the ID of the invoice to update
     * @param invoiceDTO the new invoice data
     * @return the updated invoice as a DTO
     */
    @Secured("ROLE_USER")
    @PutMapping({"/{invoiceId}/", "/{invoiceId}"})
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param invoiceId the ID of the invoice to delete
     */
    @DeleteMapping({"/{invoiceId}", "/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId) {
        invoiceService.remove(invoiceId);
    }

    /**
     * Returns invoice statistics.
     *
     * @return a ResponseEntity containing invoice statistics with HTTP status 200 (OK)
     */
    @GetMapping({"/statistics/", "/statistics"})
    public ResponseEntity<InvoiceStatisticsDTO> getStatistics() {
        InvoiceStatisticsDTO statisticsDTO = invoiceService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}