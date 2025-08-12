package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
<<<<<<< Updated upstream
=======
import cz.itnetwork.entity.UserEntity;
>>>>>>> Stashed changes
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

    /**
     * Vyhledá všechny faktury podle zadané specifikace a vrátí výsledky stránkovaně.
     *
     * @param specification filtr specifikující podmínky vyhledávání
     * @param pageable informace o stránkování a řazení
     * @return stránka faktur vyhovujících specifikaci
     */
    Page<InvoiceEntity> findAll(Specification<InvoiceEntity> specification, Pageable pageable);

    /**
     * Najde všechny faktury, kde prodávající má dané identifikační číslo.
     *
     * @param identificationNUmber - identifikační číslo prodávajícího (IČO)
     * @return seznam faktur odpovídajících zadanému identifikačnímu číslu prodávajícího
     */
    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNUmber);

    /**
     * Najde všechny faktury, kde kupující má zadané identifikační číslo.
     *
     * @param identificationNumber identifikační číslo kupujícího (IČO)
     * @return seznam faktur odpovídajících zadanému identifikačnímu číslu kupujícího
     */
    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);

    /**
     * Spočítá součet cen všech faktur, které byly vystaveny v aktuálním roce.
     *
     * @return celková suma cen faktur za aktuální rok jako BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice WHERE EXTRACT(YEAR FROM issued) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
    BigDecimal sumPricesForCurrentYear();

    /**
     * Spočítá celkový součet cen všech faktur za celou dobu.
     *
     * @return celková suma všech faktur jako BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice", nativeQuery = true)
    BigDecimal allTimeSum();


}
