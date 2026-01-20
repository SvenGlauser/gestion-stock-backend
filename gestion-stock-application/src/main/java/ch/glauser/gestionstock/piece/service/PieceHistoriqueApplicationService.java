package ch.glauser.gestionstock.piece.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
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
    PieceHistoriqueDto get(Long id);

    /**
     * Récupère les mouvements de pièce
     *
     * @param automaticSearchQuery Paramètres de recherche
     * @return Une liste de mouvements de pièce
     */
    SearchResult<PieceHistoriqueDto> search(AutomaticSearchQuery automaticSearchQuery);

    /**
     * Supprime une entrée de l'historique
     *
     * @param idPieceHistorique Id de l'historique à supprimer
     */
    void delete(Long idPieceHistorique);
}
