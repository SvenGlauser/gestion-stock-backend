package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;

/**
 * Service métier de gestion des pièces
 */
public interface PieceService {
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
     * Récupère les pièces pour l'autocomplétion
     *
     * @param searchValue Valeur de recherche
     * @return Une liste de pièces paginée
     */
    SearchResult<Piece> autocompletePiece(String searchValue);

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
}
