package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementace služby pro práci s entitou Person.
 * *
 * Obsahuje logiku pro přidávání, aktualizaci, odstranění
 * a získávání informací o osobách.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Přidá novou osobu do systému.
     * *
     *  Nejprve převede vstupní DTO na entitu, kterou uloží do databáze.
     *  Výsledek následně převede zpět na DTO a vrátí.
     *
     * @param personDTO - objekt s údaji o osobě
     * @return - uložená osoba ve formě DTO
     */
    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    /**
     * Skryje osobu se zadaným ID (soft delete).
     * *
     *  Vyhledá osobu podle ID a nastaví příznak `hidden` na true,
     *  čímž se označí jako skrytá místo fyzického smazání z databáze.
     *  Pokud osoba neexistuje, metoda chybu ignoruje.
     *
     * @param personId - ID osoby, která má být skryta
     */
    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored){

        }
    }

    /**
     * Vrátí stránkovaný seznam všech nezakrytých (aktivních) osob.
     * *
     * Načítá pouze záznamy, které nejsou označeny jako skryté (`hidden = false`),
     * a převádí je na DTO objekty.
     *
     * @param pageable - informace o stránkování (číslo stránky, velikost, řazení)
     * @return - stránka s DTO objekty osob
     */
    @Override
    public Page<PersonDTO> getAll(Pageable pageable) {
        return personRepository.findByHidden(false, pageable)
                .map(personMapper::toDTO);
    }

    /**
     * Vrátí detail osoby podle jejího ID.
     * *
     * Načte entitu osoby pomocí zadaného ID a převede ji na DTO.
     * Pokud osoba neexistuje, metoda vyvolá výjimku.
     *
     * @param personId -  ID hledané osoby
     * @return -  DTO s údaji o osobě
     * @throws NotFoundException - pokud osoba s daným ID neexistuje
     */
    @Override
    public PersonDTO getPerson(Long personId) {
        PersonEntity entity = fetchPersonById(personId);
        return personMapper.toDTO(entity);
    }

    /**
     * Aktualizuje osobu podle ID.
     * *
     * Nejprve označí původní záznam jako skrytý (hidden = true),
     * čímž zachová historii dat. Poté vytvoří novou entitu na základě
     * poskytnutého DTO (s novým ID) a uloží ji jako nový záznam.
     *
     * @param personId - ID osoby, která se má aktualizovat
     * @param personDTO - nové údaje osoby
     * @return - DTO nově uložené osoby
     */
    @Override
    public PersonDTO updatePerson(Long personId, PersonDTO personDTO){
        PersonEntity entity = fetchPersonById(personId);
        entity.setHidden(true);
        personRepository.save(entity);
        personDTO.setId(null);
        entity = personMapper.toEntity(personDTO);
        PersonEntity saved = personRepository.save(entity);

        return personMapper.toDTO(saved);
    }

    /**
     * Vrací statistiky tržeb jednotlivých osob.
     * *
     * Získá agregovaná data (např. součet tržeb) z databáze pomocí dotazu v repository
     * a převede je do seznamu DTO objektů pro další zpracování nebo zobrazení.
     *
     * @return -  seznam statistik osob s ID, jménem a celkovými tržbami
     */
    @Override
    public List<PersonStatisticDTO> getPersonStatistic(){
        List<Tuple> tuples;
        tuples = personRepository.getPersonSumPrice();

        return tuples.stream()
                .map(tuple -> new PersonStatisticDTO(
                        tuple.get("personId", Long.class),
                        tuple.get("personName", String.class),
                        tuple.get("revenue", BigDecimal.class)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Vyhledá osobu podle ID.
     * *
     * Pokud osoba s daným ID neexistuje, vyvolá výjimku {@link NotFoundException}.
     * Slouží jako pomocná metoda pro centralizaci načítání osob z databáze.
     *
     * @param id ID hledané osoby
     * @return entita osoby
     * @throws NotFoundException pokud osoba nebyla nalezena
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}
