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
 * Kontroler pro práci s fakturami podle identifikačního čísla (IČO)
 * Poskytuje koncové body pro získání faktur prodejce a nakupíjícího subjektu
 *
 * Cesta je vedená jako /api/identification/{identificationNumber},
 * kde {identificationNumber} je unikátní identifikátor osoby (IČO)
 */
@RestController
@RequestMapping("/api/identification/{identificationNumber}")
public class IdentificationController {

    /**
     * Injektuje (vkládá) instanci služby InvoiceService.
     * *
     * Díky anotaci @Autowired zajistí Spring, že zde bude
     * dostupný správný objekt InvoiceService, který se používá
     * pro práci s fakturami.
     */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Vrací seznam všech faktur kde je osoba (firma) jako prodejce podle identifikačního čísla (IČO)
     * @param identificationNumber - identifikační číslo osoby (IČO), podle které se faktury vyhledávají
     * @return seznam faktur typu "sales" odpovídající zadanému IČO
     */
    @GetMapping({"/sales/", "sales"})
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllSalesByIdentificationNumber(identificationNumber);
    }

    /**
     * Vrací seznam všech faktur kde je osoba (firma) jako nakupující podle identifikačního čísla (IČO)
     * @param identificationNumber - identifikační číslo osoby (IČO), podle které se faktury vyhledávají
     * @return seznam faktur typu "purchases" odpovídající zadanému IČO
     */
    @GetMapping({"/purchases/", "/purchases"})
    public List<InvoiceDTO> getAllPurchasesIdentificationNumber(@PathVariable String identificationNumber){
        return invoiceService.getAllPurchasesByIdentificationNumber(identificationNumber);
    }


}
