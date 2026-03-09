package ch.glauser.gestionstock.machine.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Service;

import java.util.List;

/**
 * Service de gestion des services
 */
public interface ServiceService {
    /**
     * Récupère un service
     *
     * @param id Id d'un service à récupérer
     * @return Le service ou null
     */
    Service get(Long id);

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
}
