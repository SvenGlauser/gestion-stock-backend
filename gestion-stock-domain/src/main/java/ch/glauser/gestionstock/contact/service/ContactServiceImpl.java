package ch.glauser.gestionstock.contact.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.contact.repository.ContactRepository;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des contacts
 */
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    public static final String FIELD_CONTACT = "contact";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_CONTACT_INEXISTANTE = "Impossible de supprimer ce contact car il n'existe pas";
    public static final String ERROR_SUPPRESSION_CONTACT_IMPOSSIBLE_EXISTE_MACHINE = "Impossible de supprimer ce contact car il existe une machine liée";

    private final ContactRepository contactRepository;

    private final MachineRepository machineRepository;

    @Override
    public Contact getContact(Long id) {
        Validator.of(ContactServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.contactRepository.getContact(id);
    }

    @Override
    public SearchResult<Contact> searchContact(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.contactRepository.searchContact(searchRequest);
    }

    @Override
    public Contact createContact(Contact contact) {
        Validator.of(ContactServiceImpl.class)
                .validateNotNull(contact, FIELD_CONTACT)
                .execute();

        contact.validate();

        return this.contactRepository.createContact(contact);
    }

    @Override
    public Contact modifyContact(Contact contact) {
        Validator.of(ContactServiceImpl.class)
                .validateNotNull(contact, FIELD_CONTACT)
                .execute();

        contact.validateModify();

        return this.contactRepository.modifyContact(contact);
    }

    @Override
    public void deleteContact(Long id) {
        Validator.of(ContactServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.validateContactExist(id);
        this.validatePasUtiliseParMachine(id);

        this.contactRepository.deleteContact(id);
    }

    /**
     * Valide que le contact existe
     * @param id Id du contact à supprimer
     */
    private void validateContactExist(Long id) {
        Contact contactToDelete = this.getContact(id);

        if (Objects.isNull(contactToDelete)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_CONTACT_INEXISTANTE,
                    FIELD_CONTACT,
                    ContactServiceImpl.class));
        }
    }

    /**
     * Valide que le contact n'est pas utilisé par une machine
     * @param id Id du contact à supprimer
     */
    private void validatePasUtiliseParMachine(Long id) {
        if (this.machineRepository.existMachineWithIdContact(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_CONTACT_IMPOSSIBLE_EXISTE_MACHINE,
                    FIELD_CONTACT,
                    ContactServiceImpl.class));
        }
    }
}
