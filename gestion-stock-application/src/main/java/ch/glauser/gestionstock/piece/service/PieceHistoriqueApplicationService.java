package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.dto.PieceHistoriqueDto;

/**
 * Service applicatif de gestion des mouvements de pièces
 */
public interface PieceHistoriqueApplicationService {
    /**
     * Récupère un mouvement de pièce
     *
     * @param id Id à récupérer
     * @return L'historique du mouvement
     */
    PieceHistoriqueDto getPieceHistorique(Long id);

    /**
     * Récupère les mouvements de pièce
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de mouvements de pièce
     */
    SearchResult<PieceHistoriqueDto> searchPieceHistorique(SearchRequest searchRequest);
}
