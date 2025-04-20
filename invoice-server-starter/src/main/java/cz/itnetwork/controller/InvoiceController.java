package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     *
     * @param invoiceDTO
     * @return
     */
    @PostMapping("")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.createInvoice(invoiceDTO);
    }

    /**
     *
     * @return
     */
    @GetMapping({"", "/"})
    public List<InvoiceDTO> getAll(){
        return invoiceService.getAll();
    }

    /**
     *
     * @param invoiceId
     * @return
     */
    @GetMapping({"/{invoiceId}/", "/{invoiceId}"})
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    /**
     *
     * @param invoiceId
     * @param invoiceDTO
     * @return
     */
    @PutMapping({"/{invoiceId}/","/{invoiceId}"})
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    /**
     *
     * @param invoiceId
     */
    @DeleteMapping({"/{invoiceId}", "/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId){
        invoiceService.remove(invoiceId);
    }

    /**
     *
     * @return
     */
    @GetMapping({"/statistics/", "/statistics"})
    public ResponseEntity<InvoiceStatisticsDTO> getStatistics(){
        InvoiceStatisticsDTO statisticsDTO = invoiceService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
