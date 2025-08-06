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
 * The @RequiredArgsConstructor annotation generates a constructor with all final fields (for easier initialization).
 */
@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        /*
         * If the minimum price filter is set, add a condition
         * that the invoice price must be greater than or equal to this value.
         */
        if (filter.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }

        /*
         * If the maximum price filter is set, add a condition
         * that the invoice price must be less than or equal to this value.
         */
        if (filter.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }

        // If a product name filter is set, add a condition
        // to search for all invoices where the 'product' column contains the specified text.
        if (filter.getProduct() != null) {
            predicates.add(criteriaBuilder.like(root.get("product"), "%" + filter.getProduct() + "%"));
        }

        /*
         * If a filter by seller ID is set,
         * create a JOIN between the invoice and the seller person,
         * and add a condition to filter invoices by this seller ID.
         */
        if (filter.getSellerID() != null) {
            // Create a JOIN between InvoiceEntity and its SELLER property (the seller person)
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);

            // Add a condition that the seller's ID must match the filter value
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerID()));
        }

        /*
         * If a filter by buyer ID is set,
         * create a JOIN between the invoice and the buyer person,
         * and add a condition to select only invoices where the buyer has the specified ID.
         */
        if (filter.getBuyerID() != null) {
            // Create a JOIN between InvoiceEntity and its BUYER property (the buyer person)
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);

            // Add a condition that the buyer's ID must match the filter value
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerID()));
        }

        /*
         * Combine all conditions (predicates) using logical AND,
         * so the resulting query requires all these conditions to be met at the same time.
         */
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
