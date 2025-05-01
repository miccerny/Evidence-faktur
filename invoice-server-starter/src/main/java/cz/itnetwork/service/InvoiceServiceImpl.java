package cz.itnetwork.service;

import cz.itnetwork.constant.InvoiceRelationType;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements  InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     *
     * @param invoiceDTO
     * @return
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, null);

        InvoiceEntity save = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(save);
    }

    /**
     *
     * @return
     */
    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param identificationNumber
     * @return
     */
    @Override
    public List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.BUYER);

    }

    /**
     *
     * @param identificationNumber
     * @return
     */
    @Override
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.SELLER);
    }


    /**
     *
     * @param id
     * @return
     */
    @Override
    public InvoiceDTO getInvoice(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        return invoiceMapper.toDTO(invoice);
    }

    /**
     *
     * @param id
     * @param invoiceDTO
     * @return
     */
    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        fetchedInvoiceById(id);
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, id);
        InvoiceEntity saved = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(saved);
    }

    /**
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    /**
     *
     * @return
     */
    @Override
    public InvoiceStatisticsDTO getStatistics() {
        BigDecimal currentYearSum = invoiceRepository.sumPricesForCurrentYear();
        BigDecimal allTimeSum = invoiceRepository.allTimeSum();
        long count = invoiceRepository.count();

        BigDecimal currentSum = currentYearSum;
        if(currentSum == null){
            currentSum = BigDecimal.ZERO;
        }

        BigDecimal allSum = allTimeSum;
        if(allSum == null){
            allTimeSum = BigDecimal.ZERO;
        }

        return new InvoiceStatisticsDTO(currentSum, allTimeSum, count);
    }

    /**
     *
     * @param id
     * @return
     */
    private InvoiceEntity fetchedInvoiceById(long id){
        try{
            return invoiceRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Faktura s ID " + id + " neexistuje.");
        }
    }

    /**
     *
     * @param invoiceDTO
     * @param id
     * @return
     */
    private InvoiceEntity prepareInvoiceEntity(InvoiceDTO invoiceDTO, Long id){
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);

        PersonEntity seller = personRepository.getReferenceById(invoice.getSeller().getId());
        PersonEntity buyer = personRepository.getReferenceById(invoice.getBuyer().getId());

        invoice.setSeller(seller);
        invoice.setBuyer(buyer);

        if (id != null) {
            invoice.setId(id);
        }

        return invoice;
    }

    /**
     *
     * @param identificationNumber
     * @param type
     * @return
     */
    private List<InvoiceDTO> getInvoiceByIdentificationNumber(String identificationNumber, InvoiceRelationType type){
        personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(()-> new NotFoundException("Person with identification number" + identificationNumber + " wasn't found. "));

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








}
