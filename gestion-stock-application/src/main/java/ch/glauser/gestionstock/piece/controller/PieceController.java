package ch.glauser.gestionstock.piece.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.service.PieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/piece", produces="application/json")
@RequiredArgsConstructor
public class PieceController {

    private final PieceService pieceService;

    @GetMapping(path = "/{id}")
    public Piece get(@PathVariable(name = "id") Long id) {
        return this.pieceService.getPiece(id);
    }

    @PostMapping
    public Piece create(@RequestBody Piece piece) {
        return this.pieceService.createPiece(piece);
    }

    @PostMapping(path = "/search")
    public SearchResult<Piece> search(@RequestBody SearchRequest searchRequest) {
        return this.pieceService.searchPiece(searchRequest);
    }

    @PutMapping
    public Piece modify(@RequestBody Piece piece) {
        return this.pieceService.modifyPiece(piece);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.pieceService.deletePiece(id);
    }
}
