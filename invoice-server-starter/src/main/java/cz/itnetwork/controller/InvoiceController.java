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
 * Controller that provides an API for working with invoices.
 *
 * All invoice-related requests start with "/api/invoices".
 * This controller handles operations such as retrieving, creating, updating,
 * deleting invoices, and also provides invoice statistics.
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    /**
     * Injects an instance of the InvoiceService.
     * <p>
     * Thanks to the @Autowired annotation, Spring ensures that
     * the correct InvoiceService object is available here, which is used
     * for working with invoices.
     */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Creates a new invoice based on the input data.
     *
     * @param invoiceDTO - an object containing the data for the invoice to be created
     * @return the newly created invoice including its generated and assigned ID
     */
    @Secured("ROLE_USER")
    @PostMapping("")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.createInvoice(invoiceDTO);
    }

    /**
     * Returns a list of all invoices with a limit of 5 invoices per web page
     * and a filter object for filtering invoices by parameters.
     *
     * @param invoiceFilter - object for filtering invoices by given parameters (amount, date, etc.)
     * @param pageable - pagination parameters (page size, page number, sorting)
     * @return a paged list of invoices matching the given filters
     */
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping({"", "/"})
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, @PageableDefault(size = 5) Pageable pageable){
        return invoiceService.getAll(invoiceFilter, pageable);
    }

    /**
     * Returns the details of a specific invoice including the details
     * of the buyer and seller.
     *
     * @param invoiceId - unique ID parameter of the invoice to be loaded
     * @return the details of the invoice by its unique "invoiceId" including the buyer and seller objects
     */
    @GetMapping({"/{invoiceId}/", "/{invoiceId}"})
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    /**
     * Updates an existing invoice by its ID.
     *
     * @param invoiceId - the ID of the invoice to be updated
     * @param invoiceDTO - an object containing new invoice information
     * @return the updated invoice as a DTO
     */
    @Secured("ROLE_USER")
    @PutMapping({"/{invoiceId}/","/{invoiceId}"})
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    /**
     * Deletes an invoice by its ID.
     *
     * @param invoiceId - the ID of the invoice to be deleted
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping({"/{invoiceId}", "/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId){
        invoiceService.remove(invoiceId);
    }

    /**
     * Returns statistics related to invoices.
     *
     * @return ResponseEntity containing invoice statistics data with HTTP status 200 OK
     */
    @Secured("ROLE_USER")
    @GetMapping({"/statistics/", "/statistics"})
    public ResponseEntity<InvoiceStatisticsDTO> getStatistics(){
        InvoiceStatisticsDTO statisticsDTO = invoiceService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
