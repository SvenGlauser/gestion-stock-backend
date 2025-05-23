package ch.glauser.gestionstock.localite.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import ch.glauser.gestionstock.localite.service.LocaliteApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/localite", produces="application/json")
@RequiredArgsConstructor
public class LocaliteController {

    private final LocaliteApplicationService localiteApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<LocaliteDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.localiteApplicationService.get(id));
    }

    @PostMapping
    public ResponseEntity<LocaliteDto> create(@RequestBody LocaliteDto localite) {
        return ResponseEntity.ok(this.localiteApplicationService.create(localite));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<LocaliteDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.localiteApplicationService.search(searchRequest));
    }

    @PutMapping
    public ResponseEntity<LocaliteDto> modify(@RequestBody LocaliteDto localite) {
        return ResponseEntity.ok(this.localiteApplicationService.modify(localite));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.localiteApplicationService.delete(id);

        return ResponseEntity.ok().build();
    }
}
