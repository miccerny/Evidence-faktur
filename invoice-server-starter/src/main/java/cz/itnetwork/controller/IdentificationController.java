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
 * REST controller for working with invoices based on the identification number (IČO).
 *
 * <p>Provides endpoints for retrieving a list of invoices where the entity is:
 * <ul>
 *     <li><b>seller</b> – endpoint {@code /sales}</li>
 *     <li><b>buyer</b> – endpoint {@code /purchases}</li>
 * </ul>
 *
 * <p>The base path is:
 * {@code /api/identification/{identificationNumber}}, where
 * {@code identificationNumber} is a unique identifier of a person or company (IČO).</p>
 */
@RestController
@RequestMapping("/api/identification/{identificationNumber}")
public class IdentificationController {

    /** Service for handling invoice operations. */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Returns all invoices where the specified entity is listed as the seller.
     *
     * @param identificationNumber the identification number (IČO) of the seller
     * @return a list of invoices where the entity is the seller
     */
    @GetMapping({"/sales", "/sales/"})
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(@PathVariable String identificationNumber) {
        return invoiceService.getAllSalesByIdentificationNumber(identificationNumber);
    }

    /**
     * Returns all invoices where the specified entity is listed as the buyer.
     *
     * @param identificationNumber the identification number (IČO) of the buyer
     * @return a list of invoices where the entity is the buyer
     */
    @GetMapping({"/purchases", "/purchases/"})
    public List<InvoiceDTO> getAllPurchasesIdentificationNumber(@PathVariable String identificationNumber) {
        return invoiceService.getAllPurchasesByIdentificationNumber(identificationNumber);
    }
}
