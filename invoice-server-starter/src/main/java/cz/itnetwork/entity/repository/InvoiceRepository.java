package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
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
     * Finds all invoices matching the given specification and returns the results in a paginated format.
     *
     * @param specification the filter specifying search conditions
     * @param pageable pagination and sorting information
     * @return a page of invoices matching the specification
     */
    Page<InvoiceEntity> findAll(Specification<InvoiceEntity> specification, Pageable pageable);

    /**
     * Finds all invoices where the seller has the specified identification number.
     *
     * @param identificationNumber the seller's identification number (e.g., company ID)
     * @return a list of invoices matching the given seller identification number
     */
    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNumber);

    /**
     * Finds all invoices where the buyer has the specified identification number.
     *
     * @param identificationNumber the buyer's identification number (e.g., company ID)
     * @return a list of invoices matching the given buyer identification number
     */
    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);

    /**
     * Calculates the total sum of prices for all invoices issued in the current year.
     *
     * @return the total price sum for invoices from the current year as a BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice WHERE EXTRACT(YEAR FROM issued) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
    BigDecimal sumPricesForCurrentYear();

    /**
     * Calculates the total sum of prices for all invoices of all time.
     *
     * @return the total price sum for all invoices as a BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice", nativeQuery = true)
    BigDecimal allTimeSum();
}
