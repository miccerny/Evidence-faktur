package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Rozhraní definující základní operace pro správu osob.
 * *
 * Zahrnuje metody pro přidání, aktualizaci, odstranění
 * a načtení informací o osobách.
 */
public interface PersonService {

    /**
     * Přidá novou osobu do systému.
     *
     * @param personDTO -  data nové osoby
     * @return -  DTO právě uložené osoby s přiřazeným ID
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * Označí osobu se zadaným ID jako skrytou (soft delete).
     *
     * @param id ID osoby, která má být skryta
     */
    void removePerson(long id);

    /**
     * Vrátí stránkovaný seznam všech aktivních osob.
     *
     * @param pageable informace o stránkování a řazení výsledků
     * @return stránka obsahující DTO osob
     */
    Page<PersonDTO> getAll(Pageable pageable);

    /**
     * Vrátí osobu podle jejího ID.
     *
     * @param personId ID osoby, kterou chceme získat
     */
    PersonDTO getPerson(Long personId);

    /**
     * Aktualizuje údaje osoby podle jejího ID.
     *
     * @param personId ID osoby, kterou chceme aktualizovat
     * @param personDTO objekt s novými údaji osoby
     * @return aktualizované DTO osoby
     */
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    /**
     * Vrací statistiky osob včetně jejich tržeb.
     *
     * @return seznam statistik osob jako DTO
     */
    List<PersonStatisticDTO> getPersonStatistic();

}
