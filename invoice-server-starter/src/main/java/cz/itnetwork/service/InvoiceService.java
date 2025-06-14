package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {

    /**
     *
     * @param invoiceDTO
     * @return
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     *
     * @return
     */
    List<InvoiceDTO> getAll();

    /**
     *
     * @param id
     * @return
     */
    InvoiceDTO getInvoice(Long id);

    /**
     *
     * @param identificationNumber
     * @return
     */
    List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber);

    /**
     *
     * @param identificationNumber
     * @return
     */
    List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber);

    /**
     *
     * @param id
     * @param invoiceDTO
     * @return
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     *
     * @param id
     */
    void remove(Long id);

    /**
     *
     * @return
     */
    InvoiceStatisticsDTO getStatistics();
}
