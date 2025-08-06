package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for working with invoices by identification number (IČO).
 * <p>
 * Provides endpoints for retrieving invoices where the subject is either the seller or the buyer.
 *
 * The base path is /api/identification/{identificationNumber},
 * where {identificationNumber} is the unique ID of the person (IČO).
 */
@RestController
@RequestMapping("/api/identification/{identificationNumber}")
public class IdentificationController {

    /**
     * Injects an instance of InvoiceService.
     * <p>
     * Thanks to the @Autowired annotation, Spring will ensure
     * the correct InvoiceService object is available here, which is used
     * for working with invoices.
     */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Returns a list of all invoices where the person (company) is the seller by identification number (IČO).
     * @param identificationNumber - the identification number (IČO) used to search for invoices
     * @return list of "sales" invoices matching the given IČO
     */
    @GetMapping({"/sales/", "/sales"})
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllSalesByIdentificationNumber(identificationNumber);
    }

    /**
     * Returns a list of all invoices where the person (company) is the buyer by identification number (IČO).
     * @param identificationNumber - the identification number (IČO) used to search for invoices
     * @return list of "purchases" invoices matching the given IČO
     */
    @GetMapping({"/purchases/", "/purchases"})
    public List<InvoiceDTO> getAllPurchasesIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllPurchasesByIdentificationNumber(identificationNumber);
    }

}
