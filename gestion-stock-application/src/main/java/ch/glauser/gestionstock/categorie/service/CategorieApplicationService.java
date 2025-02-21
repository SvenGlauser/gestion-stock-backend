package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;

/**
 * Service applicatif de gestion des catégories
 */
public interface CategorieApplicationService {
    /**
     * Récupère une catégorie
     *
     * @param id Id de la catégorie à récupérer
     * @return La catégorie ou null
     */
    CategorieDto getCategorie(Long id);

    /**
     * Récupère les catégories
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de catégorie paginée
     */
    SearchResult<CategorieDto> searchCategorie(SearchRequest searchRequest);

    /**
     * Crée une catégorie
     *
     * @param categorie Catégorie à créer
     * @return La catégorie créée
     */
    CategorieDto createCategorie(CategorieDto categorie);

    /**
     * Modifie une catégorie
     *
     * @param categorie Catégorie à modifier avec les nouvelles valeurs
     * @return La catégorie modifiée
     */
    CategorieDto modifyCategorie(CategorieDto categorie);

    /**
     * Supprime une catégorie
     *
     * @param id Id de la catégorie à supprimer
     */
    void deleteCategorie(Long id);
}
