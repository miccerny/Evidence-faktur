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
     * @param hidden   flag indicating whether to search for hidden persons (true = hidden)
     * @param pageable pagination information (page size, page number, sorting)
     * @return a page of persons matching the 'hidden' status filter
     */
    Page<PersonEntity> findByHidden(boolean hidden, Pageable pageable);

    /**
     * Finds persons that belong to the given owner (user).
     *
     * @param userEntity the owner (user) entity
     * @param pageable   pagination information (page size, page number, sorting)
     * @return a page of persons owned by the given user
     */
    Page<PersonEntity> findByOwner(UserEntity userEntity, Pageable pageable);

    /**
     * Finds a person by their identification number (IČO).
     *
     * @param identificationNumber the person's identification number
     * @return an Optional containing the found person, or empty if not found
     */
    Optional<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Checks if a person with the given identification number exists.
     *
     * @param identificationNumber the person's identification number
     * @return true if a person with the given identification number exists, false otherwise
     */
    boolean existsByIdentificationNumber(String identificationNumber);

    /**
     * Returns a list of persons along with the total revenue (sum of invoice prices)
     * where the persons are the sellers.
     * Uses LEFT JOIN to include persons without invoices (their revenue will be 0).
     * Results are ordered by revenue in descending order, then by person ID in ascending order.
     *
     * @return a list of Tuples, where each contains:
     *         - personId (ID of the person),
     *         - personName (name of the person),
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
}
