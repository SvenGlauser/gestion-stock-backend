package ch.glauser.gestionstock.categorie.repository;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;

/**
 * Repository de gestion des catégories
 */
public interface CategorieRepository {
    /**
     * Récupère une catégorie
     *
     * @param id Id de la catégorie à récupérer
     * @return La catégorie ou null
     */
    Categorie getCategorie(Long id);

    /**
     * Récupère les catégories
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de catégorie paginée
     */
    SearchResult<Categorie> searchCategorie(SearchRequest searchRequest);

    /**
     * Crée une catégorie
     *
     * @param categorie Catégorie à créer
     * @return La catégorie créée
     */
    Categorie createCategorie(Categorie categorie);

    /**
     * Modifie une catégorie
     *
     * @param categorie Catégorie à modifier avec les nouvelles valeurs
     * @return La catégorie modifiée
     */
    Categorie modifyCategorie(Categorie categorie);

    /**
     * Supprime une catégorie
     *
     * @param id Id de la catégorie à supprimer
     */
    void deleteCategorie(Long id);

    /**
     * Vérifie s'il existe une catégorie avec ce nom
     *
     * @param nom Nom de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existCategorieByNom(String nom);
}
