package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceStatistics;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InvoiceService {

    /**
     *Creates a new invoice
     *
     * @param invoice Invoice to create
     * @return newly created invoice
     */
    InvoiceDTO addInvoice(InvoiceDTO invoice);

    /**
     * Fetches all invoices if the InvoiceFilter param is empty, otherwise it selects particular invoices according to the filter set
     * @param invoiceFilter Filter to narrow the selection of invoices by our needs
     * @return List of all/filtered invoices
     */
    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);

    /**
     * Fetches details about particular invoice
     *
     * @param id Invoice to show
     * @return  Selected invoice
     */
    InvoiceDTO getInvoiceDetail(long id);

    /**
     * Updates particular Invoice
     *
     * @param id ID of invoice to update
     * @param invoiceDTO Invoice
     * @return Updated invoice
     */
    InvoiceDTO updateInvoice(Long id,InvoiceDTO invoiceDTO);

    /**
     * Removes an invoice
     *
     * @param id Invoice to remove
     * @return Deleted invoice(to show the ID of removed item for example)
     */
    InvoiceDTO removeInvoice(Long id);

    /**
     * Gets us statistics of stored invoices
     *
     * @return  Current year's sum of income
     *          All time sum of income
     *          Number of invoices
     */
    InvoiceStatistics getInvoiceStatistics();


    /*
            @Override
            public InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO) {
                InvoiceEntity fetchedInvoice = invoiceRepository.getReferenceById(id);
                invoiceMapper.updateInvoice(invoiceDTO,fetchedInvoice);

                //uložení buyer/seller do PersronEntity, tak jak se pak  ukládá do DB (repository)
                PersonEntity fetchedBuyerOrSeller = personMapper.toEntity(invoiceDTO.getBuyer());
                fetchedInvoice.setBuyer(fetchedBuyerOrSeller);
                fetchedBuyerOrSeller=personMapper.toEntity(invoiceDTO.getSeller());
                fetchedInvoice.setSeller(fetchedBuyerOrSeller);

                //nezapomenout změnit BUYER & SELLER
                invoiceRepository.saveAndFlush(fetchedInvoice);
                return invoiceMapper.toDTO(fetchedInvoice);
            }

            */
}
