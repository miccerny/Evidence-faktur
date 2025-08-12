package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface defining basic operations for managing persons.
 * <p>
 * Includes methods for adding, updating, deleting,
 * and retrieving person information.
 */
public interface PersonService {

    /**
     * Adds a new person to the system.
     *
     * @param personDTO data for the new person
     * @return DTO of the newly saved person with an assigned ID
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * Marks the person with the given ID as hidden (soft delete).
     *
     * @param id ID of the person to be hidden
     */
    void removePerson(long id);

    /**
     * Returns a paginated list of all active persons.
     *
     * @param pageable pagination and sorting information
     * @return a page containing person DTOs
     */
    Page<PersonDTO> getAll(Pageable pageable);

    /**
     * Retrieves a person by their ID.
     *
     * @param personId ID of the person to retrieve
     * @return DTO of the found person
     */
    PersonDTO getPerson(Long personId);

    /**
     * Updates the data of the person with the given ID.
     *
     * @param personId ID of the person to update
     * @param personDTO object containing the new person data
     * @return updated person DTO
     */
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    /**
     * Returns statistics for persons, including their revenues.
     *
     * @return list of person statistics as DTOs
     */
    List<PersonStatisticDTO> getPersonStatistic();
}
