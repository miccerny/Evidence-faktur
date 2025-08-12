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
     * Najde osoby podle toho, zda jsou označeny jako skryté.
     *
     * @param hidden flag, jestli hledat skryté osoby (true = skryté)
     * @param pageable stránkování výsledků (velikost stránky, číslo stránky, řazení)
     * @return stránka osob, které odpovídají filtru podle stavu 'hidden'
     */
    Page<PersonEntity> findByHidden(boolean hidden, Pageable pageable);

    Page<PersonEntity> findByOwner(UserEntity userEntity, Pageable pageable);

    /**
     * Najde osobu podle jejího identifikačního čísla (IČO).
     *
     * @param identificationNumber identifikační číslo osoby
     * @return Optional obsahující nalezenou osobu nebo prázdný, pokud není nalezena
     */
    Optional<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Vrátí seznam osob spolu s celkovým součtem tržeb (cena faktur), kde jsou osoby prodávajícími.
     * Používá LEFT JOIN, aby byly zahrnuty i osoby bez faktur (jejich tržby budou 0).
     * Výsledky jsou seřazeny podle tržeb sestupně, a pak podle ID osoby vzestupně.
     *
     * @return seznam Tuple, kde každý obsahuje:
     *         - personId (ID osoby),
     *         - personName (jméno osoby),
     *         - revenue (součet cen faktur, případně 0)
     */

    boolean existsByIdentificationNumber(String identificationNumber);


    @Query(value = """
    SELECT p.id AS personId, p.name AS personName, COALESCE(SUM(i.price), 0) AS revenue
    FROM person p
    LEFT JOIN invoice i ON p.id = i.seller_id
    GROUP BY p.id, p.name
    ORDER BY revenue DESC, p.id ASC
    """, nativeQuery = true)
    List<Tuple> getPersonSumPrice();
}
