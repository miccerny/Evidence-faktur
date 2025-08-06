
package cz.itnetwork.controller;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller that provides an API for working with persons (people/entities).
 *
 * All requests related to persons start with "/api/persons".
 * This controller handles operations such as retrieving, creating, updating,
 * deleting persons, and also provides statistics.
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    /**
     * Injects an instance of PersonService.
     * <p>
     * Thanks to the @Autowired annotation, Spring ensures that
     * the correct PersonService object is available here, which is used
     * for working with persons.
     */
    @Autowired
    private PersonService personService;

    /**
     * Adds a new person to the system.
     *
     * @param personDTO - an object containing information about the person to add
     * @return - the newly created person as a DTO
     */
    @PostMapping("")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    /**
     * Returns a paginated list of persons.
     *
     * @param pageable - pagination parameters (page size, page number)
     * @return - a page containing a list of persons as DTOs
     */
    @GetMapping("")
    public Page<PersonDTO> getPersons(@PageableDefault(size = 1000) Pageable pageable) {
        return personService.getAll(pageable);
    }

    /**
     * Deletes a person by their ID.
     *
     * @param personId - the ID of the person to delete
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{personId}")
    @PreAuthorize("isAuthenticated()")
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    /**
     * Returns the details of a specific person by their unique ID.
     *
     * @param personId - the ID of the person to retrieve
     * @return - the person as a DTO
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{personId}")
    public PersonDTO getPerson(@PathVariable Long personId){
        return personService.getPerson(personId);
    }

    /**
     * Updates the details of an existing person by their ID.
     *
     * @param personId - the ID of the person to update
     * @param personDTO - object containing new information about the person
     * @return - the updated person as a DTO
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{personId}")
    public PersonDTO updatePerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO){
        return personService.updatePerson(personId, personDTO);
    }

    /**
     * Returns statistics related to all persons.
     *
     * @return - a list of objects with information about the statistics
     */
    @GetMapping({"/statistics/","/statistics"})
    public List<PersonStatisticDTO> getPersonStatistics(){
        return personService.getPersonStatistic();
    }
}

