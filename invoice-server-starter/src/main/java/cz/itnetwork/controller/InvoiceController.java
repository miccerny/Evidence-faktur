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
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     *
     * @param invoiceDTO
     * @return
     */
    @PostMapping("/invoices")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.createInvoice(invoiceDTO);
    }

    /**
     *
     * @return
     */
    @GetMapping({"/invoices", "/invoices/"})
    public List<InvoiceDTO> getAll(){
        return invoiceService.getAll();
    }

    /**
     *
     * @param invoiceId
     * @return
     */
    @GetMapping({"/invoices/{invoiceId}/", "invoices/{invoiceId}"})
    public InvoiceDTO getInvoice(@PathVariable Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    /**
     *
     * @param invoiceId
     * @param invoiceDTO
     * @return
     */
    @PutMapping({"/invoices/{invoiceId}/","/invoices/{invoiceId}"})
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    /**
     *
     * @param invoiceId
     */
    @DeleteMapping({"/invoices/{invoiceId}", "/invoices/{invoiceId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable Long invoiceId){
        invoiceService.remove(invoiceId);
    }

    /**
     *
     * @return
     */
    @GetMapping({"/invoices/statistics/", "/invoices/statistics"})
    public ResponseEntity<InvoiceStatisticsDTO> getStatistics(){
        InvoiceStatisticsDTO statisticsDTO = invoiceService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
