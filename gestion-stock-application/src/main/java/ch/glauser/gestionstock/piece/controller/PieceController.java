package ch.glauser.gestionstock.piece.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import ch.glauser.gestionstock.piece.service.PieceApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/piece", produces="application/json")
@RequiredArgsConstructor
public class PieceController {

    private final PieceApplicationService pieceApplicationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PieceDto> get(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.pieceApplicationService.getPiece(id));
    }

    @PostMapping
    public ResponseEntity<PieceDto> create(@RequestBody PieceDto piece) {
        return ResponseEntity.ok(this.pieceApplicationService.createPiece(piece));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<PieceDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.pieceApplicationService.searchPiece(searchRequest));
    }

    @PutMapping
    public ResponseEntity<PieceDto> modify(@RequestBody PieceDto piece) {
        return ResponseEntity.ok(this.pieceApplicationService.modifyPiece(piece));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        this.pieceApplicationService.deletePiece(id);

        return ResponseEntity.ok().build();
    }
}
