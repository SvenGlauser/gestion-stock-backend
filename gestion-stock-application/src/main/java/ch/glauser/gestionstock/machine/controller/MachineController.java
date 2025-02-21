package ch.glauser.gestionstock.machine.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.machine.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/machine", produces="application/json")
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @GetMapping(path = "/{id}")
    public Machine get(@PathVariable(name = "id") Long id) {
        return this.machineService.getMachine(id);
    }

    @PostMapping
    public Machine create(@RequestBody Machine machine) {
        return this.machineService.createMachine(machine);
    }

    @PostMapping(path = "/search")
    public SearchResult<Machine> search(@RequestBody SearchRequest searchRequest) {
        return this.machineService.searchMachine(searchRequest);
    }

    @PutMapping
    public Machine modify(@RequestBody Machine machine) {
        return this.machineService.modifyMachine(machine);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.machineService.deleteMachine(id);
    }
}
