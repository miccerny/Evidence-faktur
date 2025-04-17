package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getAll();

    InvoiceDTO getInvoice(Long id);

    List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber);

    List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber);

    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    void remove(Long id);

    InvoiceStatisticsDTO getStatistics();
}
