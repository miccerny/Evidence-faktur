package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.*;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a specification for filtering invoices (InvoiceEntity) using the JPA Criteria API.
 *
 * <p>The {@code @RequiredArgsConstructor} annotation generates a constructor
 * with all {@code final} fields for easier initialization.</p>
 */
@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        /*
         * If the minimum price filter is set, add a condition that
         * the invoice price must be greater than or equal to this value.
         */
        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }

        /*
         * If the maximum price filter is set, add a condition that
         * the invoice price must be less than or equal to this value.
         */
        if (filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }

        /*
         * If the product name filter is set, add a condition that
         * searches for invoices where the 'product' column contains the given text.
         */
        if (filter.getProduct() != null) {
            predicates.add(criteriaBuilder.like(root.get("product"), "%" + filter.getProduct() + "%"));
        }

        /*
         * If the seller ID filter is set, create a JOIN between the invoice and its seller,
         * and add a condition that the seller's ID must match the provided value.
         */
        if (filter.getSellerID() != null) {
            // Join between InvoiceEntity and its SELLER property
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);

            // Add condition for seller ID
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerID()));
        }

        /*
         * If the buyer ID filter is set, create a JOIN between the invoice and its buyer,
         * and add a condition that the buyer's ID must match the provided value.
         */
        if (filter.getBuyerID() != null) {
            // Join between InvoiceEntity and its BUYER property
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);

            // Add condition for buyer ID
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerID()));
        }

        /*
         * Combine all conditions (predicates) with logical AND
         * so that all filters must be satisfied simultaneously.
         */
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}