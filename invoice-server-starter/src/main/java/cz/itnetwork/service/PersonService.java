package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);

    /**
     * Fetches all non-hidden persons
     *
     * @return List of all non-hidden persons
     */
    List<PersonDTO> getAll();

    /**
     *
     * @param personId
     * @return
     */
    PersonDTO getPerson(Long personId);

    /**+
     *
     * @param personId
     * @param personDTO
     * @return
     */
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    /**
     *
     * @return
     */
    List<PersonStatisticDTO> getPersonStatistic();

}
