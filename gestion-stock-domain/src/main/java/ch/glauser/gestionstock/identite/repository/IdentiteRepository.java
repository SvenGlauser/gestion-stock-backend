package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.model.Identite;

import java.util.Set;

/**
 * Repository de gestion des identités
 */
public interface IdentiteRepository {

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
     * @param searchRequest Paramètres de recherche
     * @return Une liste d'identités paginée
     */
    SearchResult<Identite> search(SearchRequest searchRequest);

    /**
     * Vérifie s'il existe une identité avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existByIdLocalite(Long id);
}
