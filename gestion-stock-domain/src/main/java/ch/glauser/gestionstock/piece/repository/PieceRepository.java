package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.Piece;

import java.util.Optional;

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
    Optional<Piece> get(Long id);

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
     * Supprime une pièce
     *
     * @param id Id de la pièce à supprimer
     */
    void delete(Long id);

    /**
     * Vérifie s'il existe une pièce avec cette catégorie
     *
     * @param id Id de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existByIdCategorie(Long id);

    /**
     * Vérifie s'il existe une pièce avec ce fournisseur
     *
     * @param id Id du fournisseur
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existByIdFournisseur(Long id);

    /**
     * Vérifie s'il existe une pièce avec ce numéro d'inventaire
     *
     * @param numeroInventaire Numéro d'inventaire de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existByNumeroInventaire(String numeroInventaire);
}
