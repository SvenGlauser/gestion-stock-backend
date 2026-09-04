package ch.glauser.gestionstock.machine.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.dto.ServiceDto;

import java.util.List;

/**
 * Service applicatif de gestion des services
 */
public interface ServiceApplicationService {
    /**
     * Récupère un service
     *
     * @param id Id d'un service à récupérer
     * @return Le service ou null
     */
    ServiceDto get(Long id);

    /**
     * Récupère les services
     *
     * @param automaticSearchQuery Paramètres de recherche
     * @return Une liste de services paginée
     */
    SearchResult<ServiceDto> search(AutomaticSearchQuery automaticSearchQuery);

    /**
     * Rechercher tous les services liés à une machine
     * @param idMachine Id de la machine
     * @return Liste des services
     */
    List<ServiceDto> searchByMachine(Long idMachine);

    /**
     * Crée un service
     *
     * @param service Service à créer
     * @return Le service créé
     */
    ServiceDto create(ServiceDto service);

    /**
     * Modifie un service
     *
     * @param service Service à modifier avec les nouvelles valeurs
     * @return Le service modifié
     */
    ServiceDto modify(ServiceDto service);

    /**
     * Supprime un service
     *
     * @param id Id du service à supprimer
     */
    void delete(Long id);
}
