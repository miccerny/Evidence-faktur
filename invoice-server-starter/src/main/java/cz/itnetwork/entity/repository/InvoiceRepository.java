package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.UserEntity;
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
     * Finds all invoices according to the given specification and returns the results as a paged list.
     *
     * @param specification the filter specifying the search conditions
     * @param pageable pagination and sorting information
     * @return a page of invoices matching the specification
     */
    Page<InvoiceEntity> findAll(Specification<InvoiceEntity> specification, Pageable pageable);

    /**
     * Finds all invoices where the seller has the given identification number.
     *
     * @param identificationNumber the identification number (IČO) of the seller
     * @return a list of invoices matching the given seller identification number
     */
    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNumber);

    /**
     * Finds all invoices where the buyer has the given identification number.
     *
     * @param identificationNumber the identification number (IČO) of the buyer
     * @return a list of invoices matching the given buyer identification number
     */
    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);

    /**
     * Calculates the sum of prices of all invoices that were issued in the current year.
     *
     * @return the total sum of invoice prices for the current year as BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice WHERE EXTRACT(YEAR FROM issued) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
    BigDecimal sumPricesForCurrentYear();

    /**
     * Calculates the total sum of all invoice prices of all time.
     *
     * @return the total sum of all invoices as BigDecimal
     */
    @Query(value = "SELECT SUM(price) FROM invoice", nativeQuery = true)
    BigDecimal allTimeSum();

}
