package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceStatistics;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoice);


    List<InvoiceDTO> getAll();

    InvoiceDTO getInvoiceDetail(long id);

    InvoiceDTO updateInvoice(Long id,InvoiceDTO invoiceDTO);


    InvoiceDTO removeInvoice(Long id);


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
