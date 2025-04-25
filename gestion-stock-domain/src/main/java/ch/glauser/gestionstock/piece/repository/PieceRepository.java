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
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existPieceByIdCategorie(Long id);

    /**
     * Vérifie s'il existe une pièce avec ce fournisseur
     *
     * @param id Id du fournisseur
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existPieceByIdFournisseur(Long id);

    /**
     * Vérifie s'il existe une pièce avec ce numéro d'inventaire
     *
     * @param numeroInventaire Numéro d'inventaire de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existPieceByNumeroInventaire(String numeroInventaire);
}
