package ch.glauser.gestionstock.machine.controller;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.dto.ServiceDto;
import ch.glauser.gestionstock.machine.service.ServiceApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/service", produces="application/json")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceApplicationService serviceApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ServiceDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.serviceApplicationService.get(id));
    }

    @PostMapping(path = "/search/machine/{id}")
    public ResponseEntity<List<ServiceDto>> search(@PathVariable(name = "id") Long idMachine) {
        return ResponseEntity.ok(this.serviceApplicationService.searchByMachine(idMachine));
    }

    @PostMapping
    public ResponseEntity<ServiceDto> create(@RequestBody ServiceDto service) {
        return ResponseEntity.ok(this.serviceApplicationService.create(service));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<ServiceDto>> search(@RequestBody AutomaticSearchQuery automaticSearchQuery) {
        return ResponseEntity.ok(this.serviceApplicationService.search(automaticSearchQuery));
    }

    @PutMapping
    public ResponseEntity<ServiceDto> modify(@RequestBody ServiceDto service) {
        return ResponseEntity.ok(this.serviceApplicationService.modify(service));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.serviceApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
