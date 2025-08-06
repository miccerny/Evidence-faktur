package cz.itnetwork.entity.repository;


import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity, Long> {

    /**
     * Finds persons based on whether they are marked as hidden.
     *
     * @param hidden flag indicating whether to search for hidden persons (true = hidden)
     * @param pageable paging information (page size, page number, sorting)
     * @return a page of persons matching the 'hidden' status filter
     */
    Page<PersonEntity> findByHidden(boolean hidden, Pageable pageable);

    Page<PersonEntity> findByOwner(UserEntity userEntity, Pageable pageable);

    /**
     * Finds a person by their identification number (IČO).
     *
     * @param identificationNumber the person's identification number
     * @return an Optional containing the found person or empty if not found
     */
    Optional<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Returns a list of persons together with the total sum of revenue (invoice prices) where they are sellers.
     * Uses LEFT JOIN to include persons without invoices (their revenue will be 0).
     * The results are ordered by revenue descending, then by person ID ascending.
     *
     * @return a list of Tuple objects, each containing:
     *         - personId (person's ID),
     *         - personName (person's name),
     *         - revenue (sum of invoice prices, or 0 if none)
     */
    @Query(value = """
    SELECT p.id AS personId, p.name AS personName, COALESCE(SUM(i.price), 0) AS revenue
    FROM person p
    LEFT JOIN invoice i ON p.id = i.seller_id
    GROUP BY p.id, p.name
    ORDER BY revenue DESC, p.id ASC
    """, nativeQuery = true)
    List<Tuple> getPersonSumPrice();

    /**
     * Checks whether a person with the given identification number exists.
     *
     * @param identificationNumber the person's identification number
     * @return true if a person with this identification number exists, false otherwise
     */
    boolean existsByIdentificationNumber(String identificationNumber);

}
