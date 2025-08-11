package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;

/**
 * Service de gestion des mouvements de pièces
 */
public interface PieceHistoriqueService {
    /**
     * Récupère un mouvement de pièce
     *
     * @param id Id à récupérer
     * @return L'historique du mouvement
     */
    PieceHistorique get(Long id);

    /**
     * Récupère les mouvements de pièce
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de mouvements de pièce
     */
    SearchResult<PieceHistorique> search(SearchRequest searchRequest);

    /**
     * Créer un mouvement de pièce suite à la création d'une pièce
     *
     * @param newPiece Nouvelle pièce
     */
    void createFromPiece(Piece newPiece);

    /**
     * Créer un mouvement de pièce suite à la modification d'une pièce
     *
     * @param newPiece Nouvelle pièce
     * @param oldPiece Ancienne pièce
     * @param source Source
     */
    void createFromPiece(Piece newPiece, Piece oldPiece, PieceHistoriqueSource source);

    /**
     * Supprime une entrée de l'historique
     *
     * @param idPieceHistorique Id de l'historique à supprimer
     */
    void delete(Long idPieceHistorique);

    /**
     * Supprime tout l'historique d'une pièce
     *
     * @param idPiece Id de la pièce dont il faut supprimer l'historique
     */
    void deleteAllByIdPiece(Long idPiece);
}
