package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.PieceHistorique;

/**
 * Repository de gestion des mouvements de pièces
 */
public interface PieceHistoriqueRepository {
    /**
     * Récupère un mouvement de pièce
     *
     * @param id Id à récupérer
     * @return L'historique du mouvement
     */
    PieceHistorique getPieceHistorique(Long id);

    /**
     * Récupère les mouvements de pièce
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de mouvements de pièce
     */
    SearchResult<PieceHistorique> searchPieceHistorique(SearchRequest searchRequest);

    /**
     * Créer un mouvement de pièce
     *
     * @param pieceHistorique Mouvement à créer
     */
    void createPieceHistorique(PieceHistorique pieceHistorique);

    /**
     * Supprime tout l'historique d'une pièce
     *
     * @param idPiece Id de la pièce dont il faut supprimer l'historique
     */
    void deleteAllByIdPiece(Long idPiece);
}
