package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;

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
    Piece get(Long id);

    /**
     * Récupère les pièces
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de pièces paginée
     */
    SearchResult<Piece> search(SearchRequest searchRequest);

    /**
     * Récupère les pièces pour l'autocomplétion
     *
     * @param searchValue Valeur de recherche
     * @return Une liste de pièces paginée
     */
    SearchResult<Piece> autocomplete(String searchValue);

    /**
     * Crée une pièce
     *
     * @param piece Pièce à créer
     * @return La pièce créée
     */
    Piece create(Piece piece);

    /**
     * Modifie une pièce
     *
     * @param piece Pièce à modifier avec les nouvelles valeurs
     * @return La pièce modifiée
     */
    Piece modify(Piece piece);

    /**
     * Modifie une pièce
     *
     * @param piece Pièce à modifier avec les nouvelles valeurs
     * @param source Source spécifique pour l'historisation de la quantité
     * @return La pièce modifiée
     */
    Piece modify(Piece piece, PieceHistoriqueSource source);

    /**
     * Supprime une pièce
     *
     * @param id Id de la pièce à supprimer
     */
    void delete(Long id);
}
