package ch.glauser.gestionstock.contact.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.contact.service.ContactApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/contact", produces="application/json")
@RequiredArgsConstructor
public class ContactController {

    private final ContactApplicationService contactApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContactDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.contactApplicationService.getContact(id));
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@RequestBody ContactDto contact) {
        return ResponseEntity.ok(this.contactApplicationService.createContact(contact));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<ContactDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.contactApplicationService.searchContact(searchRequest));
    }

    @PutMapping
    public ResponseEntity<ContactDto> modify(@RequestBody ContactDto contact) {
        return ResponseEntity.ok(this.contactApplicationService.modifyContact(contact));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.contactApplicationService.deleteContact(id);

        return ResponseEntity.ok().build();
    }
}
