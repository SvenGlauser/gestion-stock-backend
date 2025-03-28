package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.piece.dto.PieceHistoriqueDto;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueConstantes;
import ch.glauser.gestionstock.piece.repository.PieceHistoriqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des mouvements de pièces
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PieceHistoriqueApplicationServiceImpl implements PieceHistoriqueApplicationService {

    private final PieceHistoriqueRepository pieceHistoriqueRepository;

    @Override
    public PieceHistoriqueDto getPieceHistorique(Long id) {
        Validator.of(PieceHistoriqueApplicationServiceImpl.class)
                .validateNotNull(id, PieceHistoriqueConstantes.FIELD_ID)
                .execute();

        PieceHistorique pieceHistorique = pieceHistoriqueRepository.getPieceHistorique(id);

        return Optional.ofNullable(pieceHistorique).map(PieceHistoriqueDto::new).orElse(null);
    }

    @Override
    public SearchResult<PieceHistoriqueDto> searchPieceHistorique(SearchRequest searchRequest) {
        Validator.of(PieceHistoriqueApplicationServiceImpl.class)
                .validateNotNull(searchRequest, PieceHistoriqueConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<PieceHistorique> searchResult = this.pieceHistoriqueRepository.searchPieceHistorique(searchRequest);

        return SearchResultUtils.transformDto(searchResult, PieceHistoriqueDto::new);
    }
}
