package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;

/**
 * Repository de gestion des pièces
 */
public interface PieceRepository {
    /**
     * Récupère une pièce
     *
     * @param id Id de la pièce à récupérer
     * @return La pièce ou null
     */
    Piece getPiece(Long id);

    /**
     * Récupère les pièces
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de pièces paginée
     */
    SearchResult<Piece> searchPiece(SearchRequest searchRequest);

    /**
     * Crée une pièce
     *
     * @param piece Pièce à créer
     * @return La pièce créée
     */
    Piece createPiece(Piece piece);

    /**
     * Modifie une pièce
     *
     * @param piece Pièce à modifier avec les nouvelles valeurs
     * @return La pièce modifiée
     */
    Piece modifyPiece(Piece piece);

    /**
     * Supprime une pièce
     *
     * @param id Id de la pièce à supprimer
     */
    void deletePiece(Long id);

    /**
     * Vérifie s'il existe une pièce avec cette catégorie
     *
     * @param id Id de la catégorie
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existPieceWithIdCategorie(Long id);

    /**
     * Vérifie s'il existe une pièce avec ce fournisseur
     *
     * @param id Id du fournisseur
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existPieceWithIdFournisseur(Long id);
}
