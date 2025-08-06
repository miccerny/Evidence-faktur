package cz.itnetwork.service;

import cz.itnetwork.constant.InvoiceRelationType;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the service for managing invoices.
 * <p>
 * Contains methods for creating, updating, deleting,
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
     * Creates a new invoice based on data from InvoiceDTO.
     * Prepares the invoice entity, saves it to the database, and returns the result as a DTO.
     *
     * @param invoiceDTO data for the new invoice
     * @return the saved invoice as InvoiceDTO
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, null);

        InvoiceEntity save = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(save);
    }

    /**
     * Returns a paged list of invoices according to the given filters.
     * Uses InvoiceSpecification to apply filters and Pageable for pagination.
     *
     * @param invoiceFilter object containing filter criteria for invoices
     * @param pageable pagination information (page, size, sorting)
     * @param userEntity the user requesting the invoices
     * @return a page of invoices converted to InvoiceDTO
     */
    @Override
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable, UserEntity userEntity) {
        Specification<InvoiceEntity> invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        // If the user is not admin, only their own invoices are returned
        if(!userEntity.isAdmin()){
            Specification<InvoiceEntity> userSpec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user"), userEntity);
            invoiceSpecification = invoiceSpecification.and(userSpec);
        }
        Page<InvoiceEntity> entityPage = invoiceRepository.findAll(invoiceSpecification, pageable);
        return entityPage.map(invoiceMapper::toDTO);
    }

    /**
     * Returns a list of all purchases (invoices) where the buyer has the given identification number.
     * Uses the internal helper method getInvoiceByIdentificationNumber with the BUYER parameter.
     *
     * @param identificationNumber identification number of the buyer
     * @return a list of invoices for the given buyer identification number
     */
    @Override
    public List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.BUYER);
    }

    /**
     * Returns a list of all sales (invoices) where the seller has the given identification number.
     * Uses the internal helper method getInvoiceByIdentificationNumber with the SELLER parameter.
     *
     * @param identificationNumber identification number of the seller
     * @return a list of invoices for the given seller identification number
     */
    @Override
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.SELLER);
    }

    /**
     * Finds an invoice by its ID and converts it to a DTO.
     *
     * @param id the invoice ID
     * @return the invoice as InvoiceDTO
     */
    @Override
    public InvoiceDTO getInvoice(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        return invoiceMapper.toDTO(invoice);
    }

    /**
     * Updates an existing invoice by its ID and the new data.
     * First checks that the invoice exists, then prepares and saves the updated entity.
     *
     * @param id the ID of the invoice to update
     * @param invoiceDTO the new invoice data
     * @return the updated invoice as InvoiceDTO
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
     * First checks that the invoice exists, then deletes it from the database.
     *
     * @param id the ID of the invoice to delete
     */
    @Override
    public void remove(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    /**
     * Gets invoice statistics including sum of prices for the current year,
     * all-time sum of prices, and the total number of invoices.
     * If no value is available, uses zero instead.
     *
     * @return an InvoiceStatisticsDTO object containing these statistics
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
     * If the invoice does not exist, throws NotFoundException with an appropriate message.
     *
     * @param id the invoice ID
     * @return the found invoice entity
     * @throws NotFoundException if the invoice with the given ID does not exist
     */
    private InvoiceEntity fetchedInvoiceById(long id){
        try{
            return invoiceRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Invoice with ID " + id + " does not exist.");
        }
    }

    /**
     * Prepares the invoice entity for saving to the database based on the provided DTO.
     *
     * - Converts the DTO to an entity.
     * - Loads and sets references to the seller and buyer from the database.
     * - If an ID is provided, sets it in the entity (for update).
     *
     * @param invoiceDTO invoice data in DTO form
     * @param id optional invoice ID (used for updates)
     * @return prepared InvoiceEntity ready to save
     */
    private InvoiceEntity prepareInvoiceEntity(InvoiceDTO invoiceDTO, Long id){
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);

        PersonEntity seller = getPersonById(invoice.getSeller().getId());
        PersonEntity buyer = getPersonById((invoice.getBuyer().getId()));

        invoice.setSeller(seller);
        invoice.setBuyer(buyer);

        if (id != null) {
            invoice.setId(id);
        }

        return invoice;
    }

    /**
     * Returns a list of invoices related to a person by their identification number and relation type (buyer or seller).
     * First checks whether a person with the given identification number exists.
     * Then loads invoices where the person is either the buyer or the seller.
     * Converts the result to a list of DTOs.
     *
     * @param identificationNumber the person's identification number
     * @param type relation type to invoices (BUYER or SELLER)
     * @return list of invoices as InvoiceDTO
     * @throws NotFoundException if the person with the given identification number does not exist
     */
    private List<InvoiceDTO> getInvoiceByIdentificationNumber(String identificationNumber, InvoiceRelationType type){
        personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new NotFoundException("Person with identification number " + identificationNumber + " wasn't found. "));

        List<InvoiceEntity> invoiceEntities;
        if(type == InvoiceRelationType.BUYER){
            invoiceEntities = invoiceRepository.findByBuyerIdentificationNumber(identificationNumber);
        }else {
            invoiceEntities = invoiceRepository.findBySellerIdentificationNumber(identificationNumber);
        }
        return invoiceEntities.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Loads a person by their ID using the repository.
    private PersonEntity getPersonById(Long id) {
        return personRepository.getReferenceById(id);
    }
}
