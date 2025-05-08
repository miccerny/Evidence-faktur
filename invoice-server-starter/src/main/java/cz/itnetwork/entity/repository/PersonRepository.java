/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.entity.PersonEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity, Long> {

    Page<PersonEntity> findByHidden(boolean hidden, Pageable pageable);

    Optional<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     *
     * @return
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
