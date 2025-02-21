package ch.glauser.gestionstock.machine.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.machine.service.MachineApplicatifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/machine", produces="application/json")
@RequiredArgsConstructor
public class MachineController {

    private final MachineApplicatifService machineApplicatifService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<MachineDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.machineApplicatifService.getMachine(id));
    }

    @PostMapping
    public ResponseEntity<MachineDto> create(@RequestBody MachineDto machine) {
        return ResponseEntity.ok(this.machineApplicatifService.createMachine(machine));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<MachineDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.machineApplicatifService.searchMachine(searchRequest));
    }

    @PutMapping
    public ResponseEntity<MachineDto> modify(@RequestBody MachineDto machine) {
        return ResponseEntity.ok(this.machineApplicatifService.modifyMachine(machine));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.machineApplicatifService.deleteMachine(id);

        return ResponseEntity.ok().build();
    }
}
