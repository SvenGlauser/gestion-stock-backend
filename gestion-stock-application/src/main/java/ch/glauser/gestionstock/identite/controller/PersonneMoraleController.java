package ch.glauser.gestionstock.identite.controller;

import ch.glauser.gestionstock.identite.dto.PersonneMoraleDto;
import ch.glauser.gestionstock.identite.service.PersonneMoraleApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/identite/morale", produces="application/json")
@RequiredArgsConstructor
public class PersonneMoraleController {

    private final PersonneMoraleApplicationService personneMoraleApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonneMoraleDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.personneMoraleApplicationService.get(id));
    }

    @PostMapping
    public ResponseEntity<PersonneMoraleDto> create(@RequestBody PersonneMoraleDto personneMorale) {
        return ResponseEntity.ok(this.personneMoraleApplicationService.create(personneMorale));
    }

    @PutMapping
    public ResponseEntity<PersonneMoraleDto> modify(@RequestBody PersonneMoraleDto personneMorale) {
        return ResponseEntity.ok(this.personneMoraleApplicationService.modify(personneMorale));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.personneMoraleApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
