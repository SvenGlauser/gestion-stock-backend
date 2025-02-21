package ch.glauser.gestionstock.contact.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.contact.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/contact", produces="application/json")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping(path = "/{id}")
    public Contact get(@PathVariable(name = "id") Long id) {
        return this.contactService.getContact(id);
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return this.contactService.createContact(contact);
    }

    @PostMapping(path = "/search")
    public SearchResult<Contact> search(@RequestBody SearchRequest searchRequest) {
        return this.contactService.searchContact(searchRequest);
    }

    @PutMapping
    public Contact modify(@RequestBody Contact contact) {
        return this.contactService.modifyContact(contact);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.contactService.deleteContact(id);
    }
}
