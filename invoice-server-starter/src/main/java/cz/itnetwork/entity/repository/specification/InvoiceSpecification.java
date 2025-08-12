package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.constant.InvoiceStatus;
import cz.itnetwork.entity.*;
import cz.itnetwork.entity.filtration.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Tato třída slouží jako specifikace pro filtrování faktur (InvoiceEntity) pomocí JPA Criteria API.
 * Anotace @RequiredArgsConstructor vygeneruje konstruktor se všemi final poli (pro jednodušší inicializaci).
 */
@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        /*
         * Pokud je nastaven minimální filtr ceny, přidáme do podmínek omezení,
         * že cena faktury musí být větší nebo rovna této hodnotě.
         */
        if(filter.getMinPrice() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }

        /*
         * Pokud je nastaven maximální filtr ceny, přidáme do podmínek omezení,
         * že cena faktury musí být menší nebo rovna této hodnotě.
         */
        if(filter.getMaxPrice() !=null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }

        // Pokud je nastaven filtr podle názvu produktu, přidáme podmínku,
        // která vyhledá všechny faktury, kde sloupec 'product' obsahuje zadaný text.
        if(filter.getProduct() != null){
            predicates.add(criteriaBuilder.like(root.get("product"),"%" + filter.getProduct() + "%" ));
        }

        /*
         * Pokud je nastaven filtr podle ID prodávajícího (sellerID),
         * vytvoříme JOIN mezi fakturou a osobou prodávajícího a přidáme podmínku,
         * která filtruje faktury podle tohoto ID prodávajícího.
         *
         */
        if(filter.getSellerID() != null){
            // Propojení (JOIN) mezi InvoiceEntity a její vlastností SELLER (osoba prodávající)
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);

            // Přidání podmínky, že ID prodávajícího musí odpovídat zadanému filtru
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerID()));
        }

        /*
        * Pokud je nastaven filtr podle ID kupujícího (buyerID),
        * vytvoříme JOIN mezi fakturou a osobou kupujícího a přidáme podmínku,
        * která vybere jen faktury, kde kupující má zadané ID.
        */
        if(filter.getBuyerID() != null){
            // Propojení (JOIN) mezi InvoiceEntity a její vlastností BUYER (osoba kupující)
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);

            // Přidání podmínky, že ID kupujícího musí odpovídat zadanému filtru
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerID()));
        }

        /*
         * Spojíme všechny podmínky (predikáty) pomocí logického AND,
         * aby výsledný dotaz vyžadoval splnění všech těchto podmínek zároveň.
         */
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
