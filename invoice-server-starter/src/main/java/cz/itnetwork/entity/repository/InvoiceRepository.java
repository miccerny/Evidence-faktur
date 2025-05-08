package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
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
     *
     * @return
     */
    Page<InvoiceEntity> findAll(Specification<InvoiceEntity> specification, Pageable pageable);

    /**
     *
     * @param identificationNUmber
     * @return
     */
    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNUmber);

    /**
     *
     * @param identificationNumber
     * @return
     */
    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);

    /**
     *
     * @return
     */
    @Query(value = "SELECT SUM(price) FROM invoice WHERE EXTRACT(YEAR FROM issued) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
    BigDecimal sumPricesForCurrentYear();

    /**
     *
     * @return
     */
    @Query(value = "SELECT SUM(price) FROM invoice", nativeQuery = true)
    BigDecimal allTimeSum();


}
