package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.exceptions.InvoiceNotFoundException;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceStatistics;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;


    //napojení ID z person tabulky
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public InvoiceDTO addInvoice(InvoiceDTO invoice) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);
        //následují vazby na osobu
        PersonEntity personEntity = personRepository.getReferenceById(invoice.getBuyer().getId());
        entity.setBuyer(personEntity);
        personEntity = personRepository.getReferenceById(invoice.getSeller().getId());
        entity.setSeller(personEntity);

        entity = invoiceRepository.saveAndFlush(entity);
        return invoiceMapper.toDTO(entity);
    }


    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public InvoiceDTO getInvoiceDetail(long id) {
        InvoiceEntity invoiceEntity = invoiceRepository.getReferenceById(id);

        return invoiceMapper.toDTO(invoiceEntity);
    }

    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        InvoiceEntity invoiceToUpdate = getInvoiceOrThrow(id);

        invoiceMapper.updateInvoiceEntity(invoiceDTO, invoiceToUpdate);     //v updateEntity se musí IGNOROVAT nahrání seller & buyer, protože přes mappera bychom neměli načítat entity/DTOs

        invoiceToUpdate.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
        invoiceToUpdate.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));
        invoiceRepository.save(invoiceToUpdate);
        return invoiceMapper.toDTO(invoiceToUpdate);
    }

    @Override
    public InvoiceDTO removeInvoice(Long id) {
        InvoiceEntity fetchedInvoice = getInvoiceOrThrow(id);
        invoiceRepository.delete(fetchedInvoice);
        return invoiceMapper.toDTO(fetchedInvoice);
    }

    @Override
    public InvoiceStatistics getInvoiceStatistics() {
        return invoiceRepository.getInvoiceStatistics();
    }

    private InvoiceEntity getInvoiceOrThrow(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(InvoiceNotFoundException::new);
    }


}


