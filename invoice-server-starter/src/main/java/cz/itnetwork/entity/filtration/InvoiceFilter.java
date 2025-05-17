package cz.itnetwork.entity.filtration;

import lombok.Data;

/**
 * Třída sloužící k filtrování faktur podle různých kritérií.
 * *
 * Obsahuje pole, která umožňují zadat parametry pro vyhledávání
 * a filtrování faktur v aplikaci.
 */
@Data
public class InvoiceFilter {

    private Long buyerID;
    private Long sellerID;
    private Integer minPrice;
    private Integer maxPrice;
    private String product;
    private Integer limit=10;

}
