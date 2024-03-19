package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonStatistics;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);

    /**
     * Fetches all non-hidden persons
     *
     * @return List of all non-hidden persons
     */
    List<PersonDTO> getAll();

    /**
     * Fetches details about particular person
     *
     * @param id Person to show
     * @return Selected person
     */
    PersonDTO getPersonDetail(long id);

    /**
     * Updates particular person
     *
     * @param id ID of person to update
     * @param personDTO Person
     * @return Updated person
     */
    PersonDTO updatePerson(long id,PersonDTO personDTO);

    /**
     * Fetches sales invoices of a customer based on his Identification N°
     *
     * @param identificationNumber Person's identification N°
     * @return List of sales invoices
     */
    List<InvoiceDTO> getSalesInvoices(String identificationNumber);

    /**
     * Fetches purchases invoices of a customer based on his Identification N°
     *
     * @param identificationNumber Person's identification N°
     * @return List of sales invoices
     */
    List<InvoiceDTO> getPurchasesInvoices(String identificationNumber);

    /**
     * Gets us statistics of stored person
     *
     * @return Custmers IDs, names and SUM of all their sales invoices
     */
    List<PersonStatistics> getPersonStatistics();



}
