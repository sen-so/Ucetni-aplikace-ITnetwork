package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;
    @Column(nullable = false)
    private int invoiceNumber;
    @Column(nullable = false)
    private LocalDate issued;
    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private int vat;
    @Column
    private String note;



    //Úprava entity, na vytvoření vazební tabulky
    @ManyToOne
    private PersonEntity buyer;
    @ManyToOne
    private PersonEntity seller;

/*
    public Long getBuyer_id(){
        if(this.buyer ==null)//Ošetření proti NULL pointeru
            return null;
        return this.buyer.getId();
    }

    public Long getSeller_id(){
        if(this.seller ==null)//Ošetření proti NULL pointeru
            return null;
        return this.seller.getId();
    }

*/



}
