package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller, který poskytuje API pro práci s fakturami.
 *
 * Všechny požadavky na faktury začínají požadavkem "/api/invoices".
 * Tento controller zpracovává operace jako získání, vytvoření, úpravu,
 * smazání faktur a také získání statistik.
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

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
     * Vytvoří novou fakturu na základě vstupních údaju
     *
     * @param invoiceDTO - objekt s daty faktury, která má být vytvořena
     * @return nově vytvořená faktura včetně vygenerovaného a přiděleného ID
     */
    @PostMapping("")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.createInvoice(invoiceDTO);
    }


    /**
     * Vrací seznam všech faktur s limitem 5 faktur na jednu webovou stránku a objekt filtru pro filtrování faktur dle parametrů.
     *
     * @param invoiceFilter - objekt pro filtrování faktur dle zadaných libovolných parametrů (částka, datum atd.)
     * @param pageable - parametry stránkování (velikost stránky, stránka, řazení)
     * @return - stránkovaný seznam faktur vyhovujících zadaným filtrům
     */
    @GetMapping({"", "/"})
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, @PageableDefault(size = 5) Pageable pageable){
        return invoiceService.getAll(invoiceFilter, pageable);
    }

    /**
     *  Vrací detail konkrétní faktury včetně detailu nakupující a prodávající osoby
     *
     * @param invoiceId - ID unikátní parametr faktury, která má být načtena
     * @return - vrací detail faktury dle unikátního paramatru "invoiceId" včetně objektu nakupujícího a prodávajícího
     */
    @GetMapping({"/{invoiceId}/", "/{invoiceId}"})
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    /**
     * Aktualizuje existující fakturu podl jejího ID
     *
     * @param invoiceId - ID faktury, kterou chceme upravit
     * @param invoiceDTO - objekt obsahující nové inforamce o faktuře
     * @return - aktualizovaná faktura jako DTO
     */
    @PutMapping({"/{invoiceId}/","/{invoiceId}"})
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    /**
     * Odstraní fakturu podle jeího ID.
     *
     * @param invoiceId - ID faktury, kterou chceme smazat
     */
    @DeleteMapping({"/{invoiceId}", "/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId){
        invoiceService.remove(invoiceId);
    }

    /**
     * Vrací statistiky týkající se faktur.
     *
     * @return ResponseEntity obsahující data statistiky faktur s HTTP stavem 200 Ok.
     */
    @GetMapping({"/statistics/", "/statistics"})
    public ResponseEntity<InvoiceStatisticsDTO> getStatistics(){
        InvoiceStatisticsDTO statisticsDTO = invoiceService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
