package cz.itnetwork.service;

import cz.itnetwork.constant.InvoiceRelationType;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the InvoiceService interface.
 * <p>
 * Provides methods for creating, updating, deleting,
 * and retrieving invoice information.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     * Creates a new invoice based on the provided InvoiceDTO.
     * Prepares the invoice entity, saves it to the database,
     * and returns the resulting DTO representation.
     *
     * @param invoiceDTO data for the new invoice
     * @return the saved invoice as an InvoiceDTO
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, null);
        InvoiceEntity saved = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(saved);
    }

    /**
     * Returns a paginated list of invoices based on the given filters.
     * Uses InvoiceSpecification to apply filters and Pageable for pagination.
     *
     * @param invoiceFilter criteria for filtering invoices
     * @param pageable pagination information (page number, size, sorting)
     * @return a page of invoices converted to InvoiceDTO
     */
    @Override
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable) {
        Specification<InvoiceEntity> invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        Page<InvoiceEntity> entityPage = invoiceRepository.findAll(invoiceSpecification, pageable);
        return entityPage.map(invoiceMapper::toDTO);
    }

    /**
     * Returns all purchases (invoices) where the buyer has the given identification number.
     * Uses the internal helper method getInvoiceByIdentificationNumber with the BUYER type.
     *
     * @param identificationNumber buyer's identification number
     * @return list of invoices for the given buyer identification number
     */
    @Override
    public List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.BUYER);
    }

    /**
     * Returns all sales (invoices) where the seller has the given identification number.
     * Uses the internal helper method getInvoiceByIdentificationNumber with the SELLER type.
     *
     * @param identificationNumber seller's identification number
     * @return list of invoices for the given seller identification number
     */
    @Override
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.SELLER);
    }

    /**
     * Finds an invoice by its ID and converts it to a DTO.
     *
     * @param id the invoice ID
     * @return the invoice as an InvoiceDTO
     */
    @Override
    public InvoiceDTO getInvoice(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        return invoiceMapper.toDTO(invoice);
    }

    /**
     * Updates an existing invoice based on the provided ID and new data.
     * First ensures the invoice exists, then prepares and saves the updated entity.
     *
     * @param id the ID of the invoice to update
     * @param invoiceDTO the new invoice data
     * @return the updated invoice as an InvoiceDTO
     */
    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        fetchedInvoiceById(id);
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, id);
        InvoiceEntity saved = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(saved);
    }

    /**
     * Deletes an invoice by its ID.
     * First ensures the invoice exists, then removes it from the database.
     *
     * @param id the ID of the invoice to delete
     */
    @Override
    public void remove(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    /**
     * Retrieves invoice statistics including:
     * - total price for the current year,
     * - total price for all time,
     * - total number of invoices.
     * <p>
     * If any values are null, they are replaced with zero.
     *
     * @return InvoiceStatisticsDTO containing these statistics
     */
    @Override
    public InvoiceStatisticsDTO getStatistics() {
        BigDecimal currentYearSum = invoiceRepository.sumPricesForCurrentYear();
        BigDecimal allTimeSum = invoiceRepository.allTimeSum();
        long count = invoiceRepository.count();

        BigDecimal currentSum = currentYearSum != null ? currentYearSum : BigDecimal.ZERO;
        BigDecimal allSum = allTimeSum != null ? allTimeSum : BigDecimal.ZERO;

        return new InvoiceStatisticsDTO(currentSum, allSum, count);
    }

    /**
     * Loads an invoice by its ID using the repository.
     * If the invoice does not exist, throws a NotFoundException.
     *
     * @param id the invoice ID
     * @return the found invoice entity
     * @throws NotFoundException if no invoice with the given ID exists
     */
    private InvoiceEntity fetchedInvoiceById(long id) {
        try {
            return invoiceRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Invoice with ID " + id + " does not exist.");
        }
    }

    /**
     * Prepares an invoice entity for saving to the database based on the provided DTO.
     * <p>
     * - Converts the DTO to an entity.
     * - Loads and sets references to the seller and buyer from the database.
     * - If an ID is provided, sets it on the entity (used for updates).
     *
     * @param invoiceDTO the invoice data in DTO form
     * @param id optional invoice ID (used for updates)
     * @return a prepared InvoiceEntity ready for saving
     */
    private InvoiceEntity prepareInvoiceEntity(InvoiceDTO invoiceDTO, Long id) {
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);

        PersonEntity seller = getPersonById(invoice.getSeller().getId());
        PersonEntity buyer = getPersonById(invoice.getBuyer().getId());

        invoice.setSeller(seller);
        invoice.setBuyer(buyer);

        if (id != null) {
            invoice.setId(id);
        }

        return invoice;
    }

    /**
     * Returns a list of invoices associated with a person by their identification number
     * and the specified relation type (buyer or seller).
     * <p>
     * First ensures that the person with the given identification number exists.
     * Then retrieves invoices where the person is either the buyer or the seller.
     * The result is converted to DTO form.
     *
     * @param identificationNumber the person's identification number
     * @param type the relation type to the invoice (BUYER or SELLER)
     * @return list of invoices as InvoiceDTO
     * @throws NotFoundException if no person with the given identification number exists
     */
    private List<InvoiceDTO> getInvoiceByIdentificationNumber(String identificationNumber, InvoiceRelationType type) {
        personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new NotFoundException("Person with identification number " + identificationNumber + " was not found."));

        List<InvoiceEntity> invoiceEntities;
        if (type == InvoiceRelationType.BUYER) {
            invoiceEntities = invoiceRepository.findByBuyerIdentificationNumber(identificationNumber);
        } else {
            invoiceEntities = invoiceRepository.findBySellerIdentificationNumber(identificationNumber);
        }
        return invoiceEntities.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Loads a person entity by its ID.
     *
     * @param id the person's ID
     * @return the found person entity
     */
    private PersonEntity getPersonById(Long id) {
        return personRepository.getReferenceById(id);
    }
}
