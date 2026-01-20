package ch.glauser.gestionstock.identite.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.model.Identite;

import java.util.Set;

/**
 * Service métier de gestion des identités
 */
public interface IdentiteService {

    /**
     * Récupère les identités par leur désignation
     *
     * @param designation Désignation
     * @return Une liste d'identités
     */
    Set<Identite> findAllByDesignation(String designation);

    /**
     * Récupère les identités
     *
     * @param automaticSearchQuery Paramètres de recherche
     * @return Une liste d'identités paginée
     */
    SearchResult<Identite> search(AutomaticSearchQuery automaticSearchQuery);

    /**
     * Valide la suppression des identités
     * @param id Id de l'identité
     */
    void validateDelete(Long id);
}
