package ch.glauser.gestionstock.machine.controller;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.machine.service.MachineApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/machine", produces="application/json")
@RequiredArgsConstructor
public class MachineController {

    private final MachineApplicationService machineApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<MachineDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.machineApplicationService.get(id));
    }

    @PostMapping
    public ResponseEntity<MachineDto> create(@RequestBody MachineDto machine) {
        return ResponseEntity.ok(this.machineApplicationService.create(machine));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<MachineDto>> search(@RequestBody AutomaticSearchQuery automaticSearchQuery) {
        return ResponseEntity.ok(this.machineApplicationService.search(automaticSearchQuery));
    }

    @PutMapping
    public ResponseEntity<MachineDto> modify(@RequestBody MachineDto machine) {
        return ResponseEntity.ok(this.machineApplicationService.modify(machine));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.machineApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
