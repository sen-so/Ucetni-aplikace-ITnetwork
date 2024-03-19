package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class InvoiceSpecification  implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;


    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if( filter.getProduct() !=null){
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT),  "%" + filter.getProduct() + "%"));
        }



       if( filter.getMinPrice() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }
        if( filter.getMaxPrice() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }


        if( filter.getSellerIco() !=null){
            Join<InvoiceEntity, PersonEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.IDENTIFICATION_NUMBER), filter.getSellerIco()));
        }
        if( filter.getBuyerIco() !=null){
            Join<InvoiceEntity, PersonEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.IDENTIFICATION_NUMBER), filter.getBuyerIco()));
        }

        if (filter.getFromDate() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.DUE_DATE), filter.getFromDate()));
        }

        if (filter.getToDate() !=null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.DUE_DATE), filter.getToDate()));
        }

        return criteriaBuilder.and((predicates.toArray(new Predicate[0])));
    }
}
