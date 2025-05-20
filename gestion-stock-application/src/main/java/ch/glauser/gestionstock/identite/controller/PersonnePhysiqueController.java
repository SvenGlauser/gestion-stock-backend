package ch.glauser.gestionstock.identite.controller;

import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.service.PersonnePhysiqueApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/identite/physique", produces="application/json")
@RequiredArgsConstructor
public class PersonnePhysiqueController {

    private final PersonnePhysiqueApplicationService personnePhysiqueApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonnePhysiqueDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.personnePhysiqueApplicationService.get(id));
    }

    @PostMapping
    public ResponseEntity<PersonnePhysiqueDto> create(@RequestBody PersonnePhysiqueDto personnePhysique) {
        return ResponseEntity.ok(this.personnePhysiqueApplicationService.create(personnePhysique));
    }

    @PutMapping
    public ResponseEntity<PersonnePhysiqueDto> modify(@RequestBody PersonnePhysiqueDto personnePhysique) {
        return ResponseEntity.ok(this.personnePhysiqueApplicationService.modify(personnePhysique));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.personnePhysiqueApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
