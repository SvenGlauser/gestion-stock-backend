package ch.glauser.gestionstock.fournisseur.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.fournisseur.service.FournisseurApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fournisseur", produces="application/json")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurApplicationService fournisseurApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<FournisseurDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.fournisseurApplicationService.getFournisseur(id));
    }

    @PostMapping
    public ResponseEntity<FournisseurDto> create(@RequestBody FournisseurDto fournisseur) {
        return ResponseEntity.ok(this.fournisseurApplicationService.createFournisseur(fournisseur));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<FournisseurDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.fournisseurApplicationService.searchFournisseur(searchRequest));
    }

    @PutMapping
    public ResponseEntity<FournisseurDto> modify(@RequestBody FournisseurDto fournisseur) {
        return ResponseEntity.ok(this.fournisseurApplicationService.modifyFournisseur(fournisseur));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.fournisseurApplicationService.deleteFournisseur(id);

        return ResponseEntity.ok().build();
    }
}
