package cz.itnetwork.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long invoiceId;

    private Integer invoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issued;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String product;

    private Float price;

    private Integer vat;

    private String note;

    private PersonDTO buyer;

    private PersonDTO seller;


}
