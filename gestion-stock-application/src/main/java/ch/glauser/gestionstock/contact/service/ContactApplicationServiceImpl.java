package ch.glauser.gestionstock.contact.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.contact.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des contacts
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContactApplicationServiceImpl implements ContactApplicationService {

    public static final String FIELD_CONTACT = "contact";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final ContactService contactService;

    @Override
    public ContactDto getContact(Long id) {
        Validation.of(ContactApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Contact contact = this.contactService.getContact(id);

        return Optional.ofNullable(contact).map(ContactDto::new).orElse(null);
    }

    @Override
    public SearchResult<ContactDto> searchContact(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Contact> searchResult = this.contactService.searchContact(searchRequest);

        return SearchResultUtils.transformDto(searchResult, ContactDto::new);
    }

    @Override
    @Transactional
    public ContactDto createContact(ContactDto contact) {
        Validation.of(ContactApplicationServiceImpl.class)
                .validateNotNull(contact, FIELD_CONTACT)
                .execute();

        Contact newContact = contact.toDomain();

        Contact savedCategorie = this.contactService.createContact(newContact);

        return Optional.ofNullable(savedCategorie).map(ContactDto::new).orElse(null);
    }

    @Override
    @Transactional
    public ContactDto modifyContact(ContactDto contact) {
        Validation.of(ContactApplicationServiceImpl.class)
                .validateNotNull(contact, FIELD_CONTACT)
                .execute();

        Contact contactToUpdate = contact.toDomain();

        Contact savedCategorie = this.contactService.modifyContact(contactToUpdate);

        return Optional.ofNullable(savedCategorie).map(ContactDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void deleteContact(Long id) {
        Validation.of(ContactApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.contactService.deleteContact(id);
    }
}
