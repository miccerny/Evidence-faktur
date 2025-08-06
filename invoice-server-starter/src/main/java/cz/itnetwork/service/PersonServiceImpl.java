package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.UserRepository;
import cz.itnetwork.exceptions.EmailNotFoundException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the service for working with the Person entity.
 * <p>
 * Contains logic for adding, updating, deleting,
 * and retrieving information about persons.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new person to the system.
     * <p>
     * First converts the input DTO to an entity and saves it to the database.
     * The result is then converted back to a DTO and returned.
     *
     * @param personDTO - object with person data
     * @return - saved person as a DTO
     */
    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        if (personRepository.existsByIdentificationNumber(personDTO.getIdentificationNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A company with this identification number already exists");
        }

        if (personDTO.getIdentificationNumber() == null || personDTO.getIdentificationNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Identification number must not be empty");
        }
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    /**
     * Hides a person with the given ID (soft delete).
     * <p>
     * Looks up the person by ID and sets the `hidden` flag to true,
     * which marks it as hidden instead of deleting it from the database.
     * If the person does not exist, the method ignores the error.
     *
     * @param personId - ID of the person to hide
     */
    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {

        }
    }

    /**
     * Returns a paged list of all non-hidden (active) persons.
     * <p>
     * Loads only records that are not marked as hidden (`hidden = false`)
     * and converts them to DTO objects.
     *
     * @param pageable - paging information (page number, size, sorting)
     * @return - a page of person DTO objects
     */
    @Override
    public Page<PersonDTO> getAll(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Principal class: " + authentication.getPrincipal().getClass());
        System.out.println("Principal: " + authentication.getPrincipal());
        // 🔓 If the user is not logged in, return all public companies
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            return personRepository.findByHidden(false, pageable)
                    .map(personMapper::toDTO);
        }

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        return personRepository.findByOwner(userEntity, pageable)
                .map(personMapper::toDTO);
    }

    /**
     * Returns details of a person by their ID.
     * <p>
     * Loads the person entity using the given ID and converts it to a DTO.
     * If the person does not exist, the method throws an exception.
     *
     * @param personId - ID of the person to find
     * @return - DTO with person data
     * @throws NotFoundException - if the person with the given ID does not exist
     */
    @Override
    public PersonDTO getPerson(Long personId) {

        PersonEntity entity = fetchPersonById(personId);
        return personMapper.toDTO(entity);
    }

    /**
     * Updates a person by their ID.
     * <p>
     * First marks the original record as hidden (`hidden = true`) to keep data history.
     * Then creates a new entity from the provided DTO (with a new ID) and saves it as a new record.
     *
     * @param personId - ID of the person to update
     * @param personDTO - new person data
     * @return - DTO of the newly saved person
     */
    @Override
    public PersonDTO updatePerson(Long personId, PersonDTO personDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new EmailNotFoundException("User not found"));

        PersonEntity entity = fetchPersonById(personId);
        // Only admins or the owner can update the person
        if (!user.isAdmin() && entity.getOwner().getUserId() != user.getUserId()) {
            throw new AccessDeniedException("You do not have permission to update this person.");
        }

        entity.setHidden(true);
        personRepository.save(entity);
        personDTO.setId(null); // new record will get a new ID
        PersonEntity newEntity = personMapper.toEntity(personDTO);
        newEntity.setOwner(entity.getOwner());
        PersonEntity saved = personRepository.save(newEntity);

        return personMapper.toDTO(saved);
    }

    /**
     * Returns sales statistics for each person.
     * <p>
     * Gets aggregated data (such as total sales) from the database using a query in the repository
     * and converts it to a list of DTO objects for further processing or display.
     *
     * @return - list of person statistics with ID, name, and total sales
     */
    @Override
    public List<PersonStatisticDTO> getPersonStatistic() {
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
     * Finds a person by ID.
     * <p>
     * If the person with the given ID does not exist, throws a {@link NotFoundException}.
     * Used as a helper method to centralize person loading from the database.
     *
     * @param id ID of the person to find
     * @return person entity
     * @throws NotFoundException if the person was not found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}
