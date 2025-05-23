package ch.glauser.gestionstock.pays.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.dto.PaysDto;
import ch.glauser.gestionstock.pays.service.PaysApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pays", produces="application/json")
@RequiredArgsConstructor
public class PaysController {

    private final PaysApplicationService paysApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PaysDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.paysApplicationService.get(id));
    }

    @PostMapping
    public ResponseEntity<PaysDto> create(@RequestBody PaysDto pays) {
        return ResponseEntity.ok(this.paysApplicationService.create(pays));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<PaysDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.paysApplicationService.search(searchRequest));
    }

    @PutMapping
    public ResponseEntity<PaysDto> modify(@RequestBody PaysDto pays) {
        return ResponseEntity.ok(this.paysApplicationService.modify(pays));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.paysApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
