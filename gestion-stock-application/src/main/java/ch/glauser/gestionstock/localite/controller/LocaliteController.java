package ch.glauser.gestionstock.localite.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/localite", produces="application/json")
@RequiredArgsConstructor
public class LocaliteController {

    private final LocaliteService localiteService;

    @GetMapping(path = "/{id}")
    public Localite get(@PathVariable(name = "id") Long id) {
        return this.localiteService.getLocalite(id);
    }

    @PostMapping
    public Localite create(@RequestBody Localite localite) {
        return this.localiteService.createLocalite(localite);
    }

    @PostMapping(path = "/search")
    public SearchResult<Localite> search(@RequestBody SearchRequest searchRequest) {
        return this.localiteService.searchLocalite(searchRequest);
    }

    @PutMapping
    public Localite modify(@RequestBody Localite localite) {
        return this.localiteService.modifyLocalite(localite);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.localiteService.deleteLocalite(id);
    }
}
