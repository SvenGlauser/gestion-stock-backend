package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.dto.IdentiteLightDto;

/**
 * Service applicatif de gestion des identités
 */
public interface IdentiteApplicationService {

    /**
     * Récupère les identités
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste d'identités paginée
     */
    SearchResult<IdentiteLightDto> search(SearchRequest searchRequest);
}
