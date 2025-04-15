package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
        InvoiceEntity invoice = invoiceMapper.toEntity(invoiceDTO);

        PersonEntity seller = personRepository.getReferenceById(invoice.getSeller().getId());
        PersonEntity buyer = personRepository.getReferenceById(invoice.getBuyer().getId());

        invoice.setSeller(seller);
        invoice.setBuyer(buyer);

        InvoiceEntity save = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(save);
    }

    /**
     *
     * @return
     */
    @Override
    public List<InvoiceDTO> getAll() {
        return invoiceRepository.findAll().stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param identificationNumber
     * @return
     */
    @Override
    public List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber) {
        PersonEntity person = personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new NotFoundException("Person with identification number " + identificationNumber + " wasn't found."));

        List<InvoiceEntity> invoiceEntities = invoiceRepository.findByBuyerIdentificationNumber(identificationNumber);

        return invoiceEntities.stream()
                .map(invoiceEntity -> invoiceMapper.toDTO(invoiceEntity))
                .collect(Collectors.toList());

    }

    /**
     *
     * @param identificationNumber
     * @return
     */
    @Override
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber) {
        PersonEntity person = personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new NotFoundException("Person with identification number " + identificationNumber + " wasn't found."));

        // Vyhledáme faktury, které vystavila tato osoba
        List<InvoiceEntity> invoices = invoiceRepository.findBySellerIdentificationNumber(identificationNumber);

        // Mapa na DTO pro klienta
        return invoices.stream()
                .map(invoice -> invoiceMapper.toDTO(invoice))
                .collect(Collectors.toList());
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
        InvoiceEntity invoice = fetchedInvoiceById(id);

        PersonEntity seller = personRepository.getReferenceById(invoice.getSeller().getId());
        PersonEntity buyer = personRepository.getReferenceById(invoice.getBuyer().getId());

        invoice.setSeller(seller);
        invoice.setBuyer(buyer);
        invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice.setId(id);
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

    private PersonEntity fecthedIdentificationNumber(String identificationNumber){
        return null;
    }








}
