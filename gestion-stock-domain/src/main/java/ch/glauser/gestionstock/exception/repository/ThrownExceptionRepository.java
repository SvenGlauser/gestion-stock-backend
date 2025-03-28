package ch.glauser.gestionstock.exception.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.exception.model.ThrownException;

/**
 * Repository de gestion des exceptions
 */
public interface ThrownExceptionRepository {

    /**
     * Récupère une exception
     * @param id Id de l'exception
     * @return L'exception récupérée
     */
    ThrownException getException(Long id);

    /**
     * Récupère une liste d'exceptions
     * @param searchRequest Requête
     * @return Une liste d'exception paginée
     */
    SearchResult<ThrownException> searchExceptions(SearchRequest searchRequest);

    /**
     * Crée une exception
     * @param thrownException Exception lancée
     * @return L'exception créée
     */
    ThrownException createException(ThrownException thrownException);

    /**
     * Modifie une exception
     * @param thrownException Exception lancée
     * @return L'exception modifiée
     */
    ThrownException modifyException(ThrownException thrownException);
}
