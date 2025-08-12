package cz.itnetwork.service;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
 * Service implementation for working with the Person entity.
 * <p>
 * Contains logic for adding, updating, deleting (soft delete),
 * and retrieving person information.
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
     * First checks whether a person with the same identification number already exists.
     * Then converts the provided DTO to an entity, saves it to the database,
     * and converts the result back to a DTO.
     *
     * @param personDTO object containing person data
     * @return saved person as a DTO
     * @throws ResponseStatusException if the identification number is missing or already exists
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
     * Marks the person with the given ID as hidden (soft delete).
     * <p>
     * Finds the person by ID and sets the {@code hidden} flag to {@code true},
     * instead of physically deleting the record from the database.
     * If the person does not exist, the exception is silently ignored.
     *
     * @param personId ID of the person to be hidden
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
     * Returns a paginated list of all visible (non-hidden) persons.
     * <p>
     * - If the user is not authenticated, returns all public (non-hidden) companies.
     * - If the user is authenticated, returns only persons owned by the logged-in user.
     *
     * @param pageable pagination and sorting information
     * @return page containing person DTOs
     */
    @Override
    public Page<PersonDTO> getAll(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Principal class: " + authentication.getPrincipal().getClass());
        System.out.println("Principal: " + authentication.getPrincipal());

        // 🔓 Unauthenticated user → return all public companies
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
     * Returns the details of a person by their ID.
     *
     * @param personId ID of the person to retrieve
     * @return person DTO
     * @throws NotFoundException if the person with the given ID does not exist
     */
    @Override
    public PersonDTO getPerson(Long personId) {
        PersonEntity entity = fetchPersonById(personId);
        return personMapper.toDTO(entity);
    }

    /**
     * Updates the data of a person by ID.
     * <p>
     * Marks the original record as hidden ({@code hidden = true}) to keep history,
     * then creates and saves a new entity based on the provided DTO (with a new ID).
     *
     * @param personId  ID of the person to update
     * @param personDTO new person data
     * @return updated person as a DTO
     */
    @Override
    public PersonDTO updatePerson(Long personId, PersonDTO personDTO) {
        PersonEntity entity = fetchPersonById(personId);
        entity.setHidden(true);
        personRepository.save(entity);
        personDTO.setId(null);
        entity = personMapper.toEntity(personDTO);
        PersonEntity saved = personRepository.save(entity);

        return personMapper.toDTO(saved);
    }

    /**
     * Returns revenue statistics for each person.
     * <p>
     * Retrieves aggregated data (e.g., total revenues) from the database
     * via the repository query and converts them into DTO objects.
     *
     * @return list of person statistics (ID, name, total revenue)
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
     * Retrieves a person by their ID.
     * <p>
     * Helper method for centralizing person lookups.
     *
     * @param id ID of the person to retrieve
     * @return person entity
     * @throws NotFoundException if no person with the given ID exists
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}
