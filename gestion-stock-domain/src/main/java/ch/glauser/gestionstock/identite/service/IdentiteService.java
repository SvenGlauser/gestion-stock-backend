package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.model.Identite;

/**
 * Service métier de gestion des identités
 */
public interface IdentiteService {

    /**
     * Récupère les identités
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste d'identités paginée
     */
    SearchResult<Identite> search(SearchRequest searchRequest);

    /**
     * Valide la suppression des identités
     * @param id Id de l'identité
     */
    void validateDelete(Long id);
}
