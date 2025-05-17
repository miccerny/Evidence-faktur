package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Rozhraní definující operace pro správu faktur.
 * *
 * Zahrnuje metody pro vytváření, úpravu, odstranění
 * a získávání faktur.
 */
public interface InvoiceService {

    /**
     * Vytvoří novou fakturu podle předaných dat.
     *
     * @param invoiceDTO data faktury k vytvoření
     * @return vytvořená faktura jako InvoiceDTO
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     * Vrátí stránkovaný seznam faktur podle zadaných filtrů.
     *
     * @param invoiceFilter kritéria pro filtrování faktur
     * @param pageable informace o stránkování (stránka, velikost, řazení)
     * @return stránka faktur odpovídajících filtrům
     */
    Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable);

    /**
     * Najde fakturu podle jejího ID.
     *
     * @param id identifikátor faktury
     * @return faktura jako InvoiceDTO, pokud existuje
     */
    InvoiceDTO getInvoice(Long id);

    /**
     * Vrátí všechny prodeje (faktury), kde prodávající má zadané identifikační číslo.
     *
     * @param identificationNumber identifikační číslo prodávajícího
     * @return seznam faktur podle identifikačního čísla prodávajícího
     */
    List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber);

    /**
     * Vrátí všechny nákupy (faktury), kde kupující má zadané identifikační číslo.
     *
     * @param identificationNumber identifikační číslo kupujícího
     * @return seznam faktur odpovídajících zadanému identifikačnímu číslu kupujícího
     */
    List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber);

    /**
     * Aktualizuje existující fakturu podle jejího ID a nových dat.
     *
     * @param id identifikátor faktury, kterou chceme aktualizovat
     * @param invoiceDTO nová data faktury
     * @return aktualizovaná faktura jako InvoiceDTO
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     * Odstraní fakturu podle jejího ID.
     *
     * @param id identifikátor faktury, kterou chceme smazat
     */
    void remove(Long id);

    /**
     * Vrátí statistiky faktur (například souhrnné údaje jako počet faktur, součet cen apod.).
     *
     * @return objekt s statistikami faktur
     */
    InvoiceStatisticsDTO getStatistics();
}
