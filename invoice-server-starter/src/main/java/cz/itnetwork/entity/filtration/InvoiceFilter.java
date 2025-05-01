package cz.itnetwork.entity.filtration;

import lombok.Data;

@Data
public class InvoiceFilter {

    private Long buyerID;
    private Long sellerID;
    private Integer minPrice;
    private Integer maxPrice;
    private String product;
    private Integer limit=10;

}
