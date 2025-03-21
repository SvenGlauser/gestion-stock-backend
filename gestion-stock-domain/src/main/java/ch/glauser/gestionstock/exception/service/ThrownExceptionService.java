package ch.glauser.gestionstock.exception.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.exception.model.ThrownException;

/**
 * Service métier de gestion des exceptions
 */
public interface ThrownExceptionService {
    /**
     * Récupère une liste d'exceptions
     * @param searchRequest Requête
     * @return Une liste d'exception paginée
     */
    SearchResult<ThrownException> searchExceptions(SearchRequest searchRequest);

    /**
     * Crée une exception
     * @param exception Exception lancée
     * @return L'exception créée
     */
    ThrownException createException(Exception exception);

    /**
     * Modifie le status d'une exception
     * @param id Id de l'exception
     * @param actif Nouveau status actif ou non (true, false)
     */
    void changeStatus(Long id, Boolean actif);
}
