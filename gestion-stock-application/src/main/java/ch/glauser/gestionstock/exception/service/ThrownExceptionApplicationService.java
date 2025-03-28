package ch.glauser.gestionstock.exception.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.exception.dto.ThrownExceptionDto;

/**
 * Service applicatif de gestion des exceptions
 */
public interface ThrownExceptionApplicationService {
    /**
     * Récupère une liste d'exceptions
     * @param searchRequest Requête
     * @return Une liste d'exception paginée
     */
    SearchResult<ThrownExceptionDto> searchExceptions(SearchRequest searchRequest);

    /**
     * Modifie le status d'une exception
     * @param id Id de l'exception
     * @param actif Nouveau status actif ou non (true, false)
     */
    void changeStatus(Long id, Boolean actif);
}
