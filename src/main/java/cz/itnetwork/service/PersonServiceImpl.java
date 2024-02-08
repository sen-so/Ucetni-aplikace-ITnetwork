/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonStatistics;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonDetail(long id) {
        PersonEntity personEntity = fetchPersonById(id);

        return personMapper.toDTO(personEntity);
    }

    @Override
    public PersonDTO updatePerson(long id, PersonDTO personDTO) {
        PersonEntity personToCreate = personMapper.toEntity(personDTO);
        personToCreate = personRepository.save(personToCreate);

        PersonEntity personToDelete = fetchPersonById(id);
        personToDelete.setHidden(true);
        personRepository.save(personToDelete);

        return personMapper.toDTO(personToCreate);
    }

    @Override
    public List<InvoiceDTO> getSalesInvoices(String identificationNumber) {
        List<PersonEntity> fetchedPersons = fetchPersonByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> fetchedSalesInvoicesDTOs =new ArrayList<>();
        List<InvoiceEntity>  fetchedSalesInvoicesEntities = new ArrayList<>();

        for (PersonEntity fetchedPerson:fetchedPersons ) {
            fetchedSalesInvoicesEntities = fetchedPerson.getSales();

            fetchedSalesInvoicesDTOs.addAll(invoiceMapper.toDTOs(fetchedSalesInvoicesEntities));
        }

        return fetchedSalesInvoicesDTOs;

    }

    @Override
    public List<InvoiceDTO> getPurchasesInvoices(String identificationNumber) {
        List<PersonEntity> fetchedPersons = fetchPersonByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> fetchedPurchasesInvoicesDTOs =new ArrayList<>();
        List<InvoiceEntity>  fetchedPurchasesInvoicesEntities = new ArrayList<>();

        for (PersonEntity fetchedPerson:fetchedPersons ) {
            fetchedPurchasesInvoicesEntities = fetchedPerson.getPurchases();

            fetchedPurchasesInvoicesDTOs.addAll(invoiceMapper.toDTOs(fetchedPurchasesInvoicesEntities));
        }

        return fetchedPurchasesInvoicesDTOs;
    }

    @Override
    public List<PersonStatistics> getPersonStatistics() {
        return personRepository.getPersonStatistics();
    }


      /*     JAK TO NAPSAT PŘES STREAMY & LAMBDA VYRAZY
        personRepository.findByIdentificationNumber(identificationNumber)
                .stream()
                .map(personEntity ::getSales())
                .flatMap(List :: stream)
                .map(item -> invoiceMapper.toDTO(item))
                .collect(Collectors.toList());
    */
    // region: Private methods


    /*          List<InvoiceDTO> getSales(String idntfNumber){
            List<PersonEntity> personEntities





     */






    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
    // endregion

    private List<PersonEntity> fetchPersonByIdentificationNumber(String identificationNumber){
        return  personRepository.findByIdentificationNumber(identificationNumber)
                ;

             //   .orElseThrow(() -> new NotFoundException("Person with identification N°: " + identificationNumber + " was not found in the database."));
    }
}
