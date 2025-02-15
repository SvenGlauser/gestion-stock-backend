package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
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
}
