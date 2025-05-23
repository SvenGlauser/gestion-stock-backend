package ch.glauser.gestionstock.piece.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.dto.PieceHistoriqueDto;
import ch.glauser.gestionstock.piece.service.PieceHistoriqueApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/piece/historique", produces="application/json")
@RequiredArgsConstructor
public class PieceHistoriqueController {

    private final PieceHistoriqueApplicationService pieceHistoriqueApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PieceHistoriqueDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.pieceHistoriqueApplicationService.get(id));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<PieceHistoriqueDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.pieceHistoriqueApplicationService.search(searchRequest));
    }
}
