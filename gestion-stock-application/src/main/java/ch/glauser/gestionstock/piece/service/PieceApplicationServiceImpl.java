package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import ch.glauser.gestionstock.piece.model.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des pièces
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PieceApplicationServiceImpl implements PieceApplicationService {

    public static final String FIELD_PIECE = "piece";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final PieceService pieceService;

    @Override
    public PieceDto getPiece(Long id) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Piece pays = this.pieceService.getPiece(id);

        return Optional.ofNullable(pays).map(PieceDto::new).orElse(null);
    }

    @Override
    public SearchResult<PieceDto> searchPiece(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Piece> searchResult = this.pieceService.searchPiece(searchRequest);

        return SearchResultUtils.transformDto(searchResult, PieceDto::new);
    }

    @Override
    @Transactional
    public PieceDto createPiece(PieceDto piece) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        Piece newPiece = piece.toDomain();

        Piece savedPiece = this.pieceService.createPiece(newPiece);

        return Optional.ofNullable(savedPiece).map(PieceDto::new).orElse(null);
    }

    @Override
    @Transactional
    public PieceDto modifyPiece(PieceDto piece) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        Piece pieceToUpdate = piece.toDomain();

        Piece savedPiece = this.pieceService.modifyPiece(pieceToUpdate);

        return Optional.ofNullable(savedPiece).map(PieceDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void deletePiece(Long id) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.pieceService.deletePiece(id);
    }
}
