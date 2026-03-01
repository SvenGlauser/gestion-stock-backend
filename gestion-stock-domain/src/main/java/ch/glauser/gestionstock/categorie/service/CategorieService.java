package ch.glauser.gestionstock.categorie.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.pagination.SearchResult;

/**
 * Service métier de gestion des catégories
 */
public interface CategorieService {
    /**
     * Récupère une catégorie
     *
     * @param id Id de la catégorie à récupérer
     * @return La catégorie ou null
     */
    Categorie get(Long id);

    /**
     * Récupère les catégories
     *
     * @param automaticSearchQuery Paramètres de recherche
     * @return Une liste de catégorie paginée
     */
    SearchResult<Categorie> search(AutomaticSearchQuery automaticSearchQuery);

    /**
     * Crée une catégorie
     *
     * @param categorie Catégorie à créer
     * @return La catégorie créée
     */
    Categorie create(Categorie categorie);

    /**
     * Modifie une catégorie
     *
     * @param categorie Catégorie à modifier avec les nouvelles valeurs
     * @return La catégorie modifiée
     */
    Categorie modify(Categorie categorie);

    /**
     * Supprime une catégorie
     *
     * @param id Id de la catégorie à supprimer
     */
    void delete(Long id);
}
