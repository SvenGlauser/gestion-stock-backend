package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.validation.common.Validation;
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

    private final PieceService pieceService;

    @Override
    public PieceDto get(Long id) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        Piece pays = this.pieceService.get(id);

        return Optional.ofNullable(pays).map(PieceDto::new).orElse(null);
    }

    @Override
    public SearchResult<PieceDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, PieceConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Piece> searchResult = this.pieceService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, PieceDto::new);
    }

    @Override
    public SearchResult<PieceDto> autocomplete(String searchValue) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchValue, PieceConstantes.FIELD_SEARCH_VALUE)
                .execute();

        SearchResult<Piece> searchResult = this.pieceService.autocomplete(searchValue);

        return SearchResultUtils.transformDto(searchResult, PieceDto::new);
    }

    @Override
    @Transactional
    public PieceDto create(PieceDto piece) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        Piece newPiece = piece.toDomain();

        Piece savedPiece = this.pieceService.create(newPiece);

        return Optional.ofNullable(savedPiece).map(PieceDto::new).orElse(null);
    }

    @Override
    @Transactional
    public PieceDto modify(PieceDto piece) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        Piece pieceToUpdate = piece.toDomain();

        Piece savedPiece = this.pieceService.modify(pieceToUpdate);

        return Optional.ofNullable(savedPiece).map(PieceDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Validation.of(PieceApplicationServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        this.pieceService.delete(id);
    }
}
