package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des pièces
 */
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {

    public static final String FIELD_PIECE = "piece";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final PieceRepository pieceRepository;

    @Override
    public Piece getPiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.pieceRepository.getPiece(id);
    }

    @Override
    public SearchResult<Piece> searchPiece(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceRepository.searchPiece(searchRequest);
    }

    @Override
    public Piece createPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        piece.validate();

        return this.pieceRepository.createPiece(piece);
    }

    @Override
    public Piece modifyPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        piece.validateModify();

        return this.pieceRepository.modifyPiece(piece);
    }

    @Override
    public void deletePiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        // FIXME vérifier liaison machines

        this.pieceRepository.deletePiece(id);
    }
}
