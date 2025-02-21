package ch.glauser.gestionstock.contact.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.contact.repository.ContactRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des contacts
 */
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    public static final String FIELD_CONTACT = "contact";
    public static final String FIELD_ID = "id";
    public static final String FIELD_ID_LOCALITE = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final ContactRepository contactRepository;

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

        // FIXME Validate no machine

        this.contactRepository.deleteContact(id);
    }

    @Override
    public boolean existContactWithIdLocalite(Long id) {
        Validator.of(ContactServiceImpl.class)
                .validateNotNull(id, FIELD_ID_LOCALITE)
                .execute();

        return this.contactRepository.existContactWithIdLocalite(id);
    }
}
