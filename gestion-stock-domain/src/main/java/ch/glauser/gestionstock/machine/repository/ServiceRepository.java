package ch.glauser.gestionstock.machine.repository;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Service;

import java.util.List;
import java.util.Optional;

/**
 * Repository de gestion des services
 */
public interface ServiceRepository {
    /**
     * Récupère un service
     *
     * @param id Id d'un service à récupérer
     * @return Le service ou null
     */
    Optional<Service> get(Long id);

    /**
     * Récupère les services
     *
     * @param automaticSearchQuery Paramètres de recherche
     * @return Une liste de services paginée
     */
    SearchResult<Service> search(AutomaticSearchQuery automaticSearchQuery);

    /**
     * Rechercher tous les services liés à une machine
     * @param idMachine Id de la machine
     * @return Liste des services
     */
    List<Service> searchByMachine(Long idMachine);

    /**
     * Crée un service
     *
     * @param service Service à créer
     * @return Le service créé
     */
    Service create(Service service);

    /**
     * Modifie un service
     *
     * @param service Service à modifier avec les nouvelles valeurs
     * @return Le service modifié
     */
    Service modify(Service service);

    /**
     * Supprime un service
     *
     * @param id Id du service à supprimer
     */
    void delete(Long id);

    /**
     * Vérifie s'il existe un service avec cette machine
     *
     * @param id Id de la machine
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existsByIdMachine(Long id);
}
