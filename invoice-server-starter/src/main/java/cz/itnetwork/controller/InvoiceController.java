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
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, @PageableDefault(size = 5) Pageable pageable){
        return invoiceService.getAll(invoiceFilter, pageable);
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
