package ch.glauser.gestionstock.contact.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.entity.ContactEntity;
import ch.glauser.gestionstock.contact.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des contacts
 */
@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;

    public ContactRepositoryImpl(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;
    }

    @Override
    public Contact getContact(Long id) {
        return this.contactJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Contact> searchContact(SearchRequest searchRequest) {
        Page<ContactEntity> page = this.contactJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Contact createContact(Contact contact) {
        return this.contactJpaRepository.save(new ContactEntity(contact)).toDomain();
    }

    @Override
    public Contact modifyContact(Contact contact) {
        return this.contactJpaRepository.save(new ContactEntity(contact)).toDomain();
    }

    @Override
    public void deleteContact(Long id) {
        this.contactJpaRepository.deleteById(id);
    }

    @Override
    public boolean existContactByIdLocalite(Long id) {
        return this.contactJpaRepository.existsByIdLocalite(id);
    }
}
