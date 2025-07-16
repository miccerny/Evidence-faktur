
package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller, který poskytuje API pro práci s s osobami.
 *
 * Všechny požadavky na osoby začínají požadavkem "/api/persons".
 * Tento controller zpracovává operace jako získání, vytvoření, úpravu,
 * smazání osob a také získání statistik.
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    /**
     * Injektuje (vkládá) instanci služby PersonService.
     * *
     * Díky anotaci @Autowired zajistí Spring, že zde bude
     * dostupný správný objekt PersonService, který se používá
     * pro práci s osobami.
     */
    @Autowired
    private PersonService personService;

    /**
     * Přidá novou osobu do systému
     *
     * @param personDTO - objekt obsahující informace o osobě, kterou chceme přidat
     * @return - nově vytvořená osoba jako DTO
     */
    @PostMapping("")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {

        return  personService.addPerson(personDTO);
    }

    /**
     *  Vrací stránkovaný seznam osob
     *
     * @param pageable - - parametry stránkování (velikost stránky, číslo stárnky)
     * @return - stránka obsahující seznam osob jako DTO
     */
    @GetMapping("")
    public Page<PersonDTO> getPersons(@PageableDefault(size = 1000) Pageable pageable) {
        return personService.getAll(pageable);
    }

    /**
     * Odstraní osobu podle jejího ID
     *
     * @param personId - ID soby, kterou chceme smazat
     */
    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    /**
     * Vrací detail konkrétní osoby dle unikátního ID.
     *
     * @param personId - ID osoby, kterou chceme získat
     * @return - osoba jako DTO
     */
    @GetMapping("/{personId}")
    public PersonDTO getPerson(@PathVariable Long personId){
        return personService.getPerson(personId);
    }

    /**
     * Aktualizuje údaje o existující osobě podle jejího ID.
     *
     * @param personId - ID osoby, kterou chceme upravit
     * @param personDTO - objekt obsahující nové inforamce o osobě
     * @return - aktualizovaná osoba jako DTO
     */
    @PutMapping("/{personId}")
    public PersonDTO updatePerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO){
        return personService.updatePerson(personId, personDTO);
    }

    /**
     * Vrací statistiky týkající se všech osob.
     *
     * @return - seznam objektů s informacemi o statistikách
     */
    @GetMapping({"/statistics/","/statistics"})
    public List<PersonStatisticDTO> getPersonStatistics(){
        return personService.getPersonStatistic();
    }

}

