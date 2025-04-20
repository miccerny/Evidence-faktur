package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/identification/{identificationNumber}")
public class IdentificationController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     *
     * @param identificationNumber
     * @return
     */
    @GetMapping({"/sales/", "sales"})
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllSalesByIdentificationNumber(identificationNumber);
    }

    /**
     *
     * @param identificationNumber
     * @return
     */
    @GetMapping({"/purchases/", "/purchases"})
    public List<InvoiceDTO> getAllPurchasesIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllPurchasesByIdentificationNumber(identificationNumber);
    }


}
