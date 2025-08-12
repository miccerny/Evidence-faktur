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
 * Implementace služby pro správu faktur.
 * *
 * Obsahuje metody pro vytváření, aktualizaci,
 * mazání a získávání informací o fakturách.
 */
@Service
public class InvoiceServiceImpl implements  InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     * Vytvoří novou fakturu na základě dat z InvoiceDTO.
     * Připraví entitu faktury, uloží ji do databáze a vrátí výslednou DTO reprezentaci.
     *
     * @param invoiceDTO data nové faktury
     * @return uložená faktura jako InvoiceDTO
     */
    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, null);

        InvoiceEntity save = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(save);
    }

    /**
     * Vrátí stránkovaný seznam faktur podle zadaných filtrů.
     * Používá InvoiceSpecification k aplikaci filtrů a Pageable k stránkování výsledků.
     *
     * @param invoiceFilter objekt obsahující kritéria pro filtrování faktur
     * @param pageable informace o stránkování (stránka, velikost, řazení)
     * @return stránka faktur převedených na InvoiceDTO
     */
    @Override
<<<<<<< Updated upstream
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable, UserEntity userEntity) {
        Specification<InvoiceEntity> invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        // If the user is not admin, only their own invoices are returned
        if(!userEntity.isAdmin()){
            Specification<InvoiceEntity> userSpec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("user"), userEntity);
            invoiceSpecification = invoiceSpecification.and(userSpec);
        }
=======
    public Page<InvoiceDTO> getAll(InvoiceFilter invoiceFilter, Pageable pageable) {
        Specification<InvoiceEntity> invoiceSpecification = new InvoiceSpecification(invoiceFilter);

>>>>>>> Stashed changes
        Page<InvoiceEntity> entityPage = invoiceRepository.findAll(invoiceSpecification, pageable);
        return entityPage.map(invoiceMapper::toDTO);
    }

    /**
     * Vrátí seznam všech nákupů (faktur), kde kupující má zadané identifikační číslo.
     * Metoda využívá interní pomocnou metodu getInvoiceByIdentificationNumber s parametrem BUYER.
     *
     * @param identificationNumber identifikační číslo kupujícího
     * @return seznam faktur odpovídajících zadanému identifikačnímu číslu kupujícího
     */
    @Override
    public List<InvoiceDTO> getAllPurchasesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.BUYER);

    }

    /**
     * Vrátí seznam všech prodejů (faktur), kde prodávající má zadané identifikační číslo.
     * Metoda používá interní pomocnou metodu getInvoiceByIdentificationNumber s parametrem SELLER.
     *
     * @param identificationNumber identifikační číslo prodávajícího
     * @return seznam faktur odpovídajících zadanému identifikačnímu číslu prodávajícího
     */
    @Override
    public List<InvoiceDTO> getAllSalesByIdentificationNumber(String identificationNumber) {
        return getInvoiceByIdentificationNumber(identificationNumber, InvoiceRelationType.SELLER);
    }


    /**
     * Najde fakturu podle jejího ID a převede ji na DTO.
     *
     * @param id identifikátor faktury
     * @return faktura jako InvoiceDTO
     */
    @Override
    public InvoiceDTO getInvoice(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        return invoiceMapper.toDTO(invoice);
    }

    /**
     * Aktualizuje existující fakturu podle zadaného ID a nových dat.
     * Nejprve ověří, že faktura s daným ID existuje, poté připraví a uloží aktualizovanou entitu.
     *
     * @param id identifikátor faktury, kterou chceme aktualizovat
     * @param invoiceDTO nová data faktury
     * @return aktualizovaná faktura jako InvoiceDTO
     */
    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        fetchedInvoiceById(id);
        InvoiceEntity invoice = prepareInvoiceEntity(invoiceDTO, id);
        InvoiceEntity saved = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(saved);
    }

    /**
     * Odstraní fakturu podle zadaného ID.
     * Nejprve ověří, že faktura existuje, pak ji smaže z databáze.
     *
     * @param id identifikátor faktury, kterou chceme odstranit
     */
    @Override
    public void remove(Long id) {
        InvoiceEntity invoice = fetchedInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    /**
     * Získá statistiky faktur zahrnující součet cen za aktuální rok,
     * celkový součet cen za celou dobu a počet všech faktur.
     * Pokud nejsou k dispozici žádné hodnoty, použije místo nich nulu.
     *
     * @return objekt InvoiceStatisticsDTO obsahující tyto statistiky
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
     * Načte fakturu podle jejího ID pomocí repository.
     * Pokud faktura neexistuje, hodí výjimku NotFoundException s vhodnou zprávou.
     *
     * @param id identifikátor faktury
     * @return nalezená entita faktury
     * @throws NotFoundException pokud faktura s daným ID neexistuje
     */
    private InvoiceEntity fetchedInvoiceById(long id){
        try{
            return invoiceRepository.getReferenceById(id);
        }catch (EntityNotFoundException e){
            throw new NotFoundException("Faktura s ID " + id + " neexistuje.");
        }
    }

    /**
     * Připraví entitu faktury pro uložení do databáze na základě předané DTO.
     *
     * - Převádí DTO na entitu.
     * - Načte a nastaví reference na prodávajícího a kupujícího z databáze.
     * - Pokud je zadáno ID, nastaví ho do entity (pro update).
     *
     * @param invoiceDTO data faktury ve formě DTO
     * @param id volitelné ID faktury (používá se při aktualizaci)
     * @return připravená InvoiceEntity připravená k uložení
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
     * Vrátí seznam faktur spojených s osobou podle jejího identifikačního čísla a typu vztahu (kupující nebo prodávající).
     * Nejprve ověří, zda osoba s daným identifikačním číslem existuje.
     * Podle typu vztahu načte faktury, kde je osoba buď kupujícím, nebo prodávajícím.
     * Výsledek převede na seznam DTO.
     *
     * @param identificationNumber identifikační číslo osoby
     * @param type typ vztahu k fakturám (BUYER nebo SELLER)
     * @return seznam faktur jako InvoiceDTO
     * @throws NotFoundException pokud osoba s daným identifikačním číslem neexistuje
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

    private PersonEntity getPersonById(Long id) {
        return personRepository.getReferenceById(id);
    }
<<<<<<< Updated upstream
=======









>>>>>>> Stashed changes
}
