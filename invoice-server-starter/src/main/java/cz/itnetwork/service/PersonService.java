package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface defining basic operations for managing persons (people/entities).
 * <p>
 * Includes methods for adding, updating, deleting,
 * and retrieving information about persons.
 */
public interface PersonService {

    /**
     * Adds a new person to the system.
     *
     * @param personDTO - data for the new person
     * @return - DTO of the newly saved person with assigned ID
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * Marks the person with the given ID as hidden (soft delete).
     *
     * @param id ID of the person to hide
     */
    void removePerson(long id);

    /**
     * Returns a paged list of all active persons.
     *
     * @param pageable information about pagination and sorting of the results
     * @return a page containing person DTOs
     */
    Page<PersonDTO> getAll(Pageable pageable);

    /**
     * Returns a person by their ID.
     *
     * @param personId ID of the person to retrieve
     */
    PersonDTO getPerson(Long personId);

    /**
     * Updates the details of a person by their ID.
     *
     * @param personId ID of the person to update
     * @param personDTO object with the new person details
     * @return updated person DTO
     */
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    /**
     * Returns statistics about persons, including their total sales (revenue).
     *
     * @return a list of person statistics as DTOs
     */
    List<PersonStatisticDTO> getPersonStatistic();
}
