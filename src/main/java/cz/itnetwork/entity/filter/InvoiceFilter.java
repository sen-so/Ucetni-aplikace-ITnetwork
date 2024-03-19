package cz.itnetwork.entity.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceFilter {

    private String buyerIco;
    private String sellerIco;
    private String product;

    private Double minPrice;
    private Double maxPrice;

    private LocalDate fromDate;
    private LocalDate toDate;

    private Integer limit = 10;

}
