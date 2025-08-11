package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.PieceHistorique;

import java.util.List;
import java.util.Optional;

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
    Optional<PieceHistorique> get(Long id);

    /**
     * Récupère le dernier mouvement de pièce
     *
     * @param idPiece Id de la pièce
     * @return L'historique du mouvement
     */
    PieceHistorique getLastHistoriqueFromPieceId(Long idPiece);

    /**
     * Récupère les mouvements de pièce
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de mouvements de pièce
     */
    SearchResult<PieceHistorique> search(SearchRequest searchRequest);

    /**
     * Recherche tout l'historique d'une pièce
     *
     * @param idPiece Id de la pièce
     *
     * @return Une liste d'historique
     */
    List<PieceHistorique> findAllByIdPiece(Long idPiece);

    /**
     * Créer un mouvement de pièce
     *
     * @param pieceHistorique Mouvement à créer
     */
    void create(PieceHistorique pieceHistorique);

    /**
     * Supprime un historique de pièce
     *
     * @param id Id de l'historique de la pièce à supprimer
     */
    void delete(Long id);

    /**
     * Supprime tout l'historique d'une pièce
     *
     * @param idPiece Id de la pièce dont il faut supprimer l'historique
     */
    void deleteAllByIdPiece(Long idPiece);
}
