package ch.glauser.gestionstock.fournisseur.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.service.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fournisseur", produces="application/json")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @GetMapping(path = "/{id}")
    public Fournisseur get(@PathVariable(name = "id") Long id) {
        return this.fournisseurService.getFournisseur(id);
    }

    @PostMapping
    public Fournisseur create(@RequestBody Fournisseur fournisseur) {
        return this.fournisseurService.createFournisseur(fournisseur);
    }

    @PostMapping(path = "/search")
    public SearchResult<Fournisseur> search(@RequestBody SearchRequest searchRequest) {
        return this.fournisseurService.searchFournisseur(searchRequest);
    }

    @PutMapping
    public Fournisseur modify(@RequestBody Fournisseur fournisseur) {
        return this.fournisseurService.modifyFournisseur(fournisseur);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.fournisseurService.deleteFournisseur(id);
    }
}
