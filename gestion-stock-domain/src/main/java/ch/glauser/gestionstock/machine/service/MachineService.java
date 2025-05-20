package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Machine;

/**
 * Service métier de gestion des machines
 */
public interface MachineService {
    /**
     * Récupère une machine
     *
     * @param id Id de la machine à récupérer
     * @return La machine ou null
     */
    Machine get(Long id);

    /**
     * Récupère les machines
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de machine paginée
     */
    SearchResult<Machine> search(SearchRequest searchRequest);

    /**
     * Crée une machine
     *
     * @param machine Machine à créer
     * @return La machine créée
     */
    Machine create(Machine machine);

    /**
     * Modifie une machine
     *
     * @param machine Machine à modifier avec les nouvelles valeurs
     * @return La machine modifiée
     */
    Machine modify(Machine machine);

    /**
     * Supprime une machine
     *
     * @param id Id de la machine à supprimer
     */
    void delete(Long id);
}
