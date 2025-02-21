package ch.glauser.gestionstock.pays.controller;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categorie", produces="application/json")
@RequiredArgsConstructor
public class PaysController {

    private final CategorieService categorieService;

    @GetMapping(path = "/{id}")
    public Categorie get(@PathVariable(name = "id") Long id) {
        return this.categorieService.getCategorie(id);
    }

    @PostMapping
    public Categorie create(@RequestBody Categorie categorie) {
        return this.categorieService.createCategorie(categorie);
    }

    @PostMapping(path = "/search")
    public SearchResult<Categorie> search(@RequestBody SearchRequest searchRequest) {
        return this.categorieService.searchCategorie(searchRequest);
    }

    @PutMapping
    public Categorie modify(@RequestBody Categorie categorie) {
        return this.categorieService.modifyCategorie(categorie);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.categorieService.deleteCategorie(id);
    }
}
