package ch.glauser.gestionstock.categorie.controller;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.categorie.service.CategorieApplicationService;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categorie", produces="application/json")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieApplicationService categorieApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategorieDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.categorieApplicationService.getCategorie(id));
    }

    @PostMapping
    public ResponseEntity<CategorieDto> create(@RequestBody CategorieDto categorie) {
        return ResponseEntity.ok(this.categorieApplicationService.createCategorie(categorie));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<CategorieDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.categorieApplicationService.searchCategorie(searchRequest));
    }

    @PutMapping
    public ResponseEntity<CategorieDto> modify(@RequestBody CategorieDto categorie) {
        return ResponseEntity.ok(this.categorieApplicationService.modifyCategorie(categorie));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.categorieApplicationService.deleteCategorie(id);

        return ResponseEntity.ok().build();
    }
}
