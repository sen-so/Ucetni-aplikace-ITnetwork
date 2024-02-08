package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query("""
            SELECT new cz.itnetwork.entity.InvoiceStatistics(
            COALESCE(SUM(this_year.price),0.0),
            COALESCE(SUM(all_years.price),0.0),
            COUNT(*) as numberOfInvoices)
            FROM invoice all_years
            LEFT JOIN invoice this_year
            ON all_years.id = this_year.id
            AND YEAR(this_year.issued) = YEAR(CURRENT_DATE)
            """)
    InvoiceStatistics getInvoiceStatistics();


    //  List<InvoiceEntity> findInvoiceById(long id);
}
