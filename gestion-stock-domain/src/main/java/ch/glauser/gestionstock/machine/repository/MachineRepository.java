package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Machine;

/**
 * Repository de gestion des machines
 */
public interface MachineRepository {
    /**
     * Récupère une machine
     *
     * @param id Id de la machine à récupérer
     * @return La machine ou null
     */
    Machine getMachine(Long id);

    /**
     * Récupère les machines
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de machine paginée
     */
    SearchResult<Machine> searchMachine(SearchRequest searchRequest);

    /**
     * Crée une machine
     *
     * @param machine Machine à créer
     * @return La machine créée
     */
    Machine createMachine(Machine machine);

    /**
     * Modifie une machine
     *
     * @param machine Machine à modifier avec les nouvelles valeurs
     * @return La machine modifiée
     */
    Machine modifyMachine(Machine machine);

    /**
     * Supprime une machine
     *
     * @param id Id de la machine à supprimer
     */
    void deleteMachine(Long id);

    /**
     * Vérifie s'il existe une machine avec ce contact
     *
     * @param id Id du contact
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existMachineByIdContact(Long id);

    /**
     * Vérifie s'il existe une machine avec cette pièce
     *
     * @param id Id de la pièce
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existMachineByIdPiece(Long id);

    /**
     * Vérifie s'il existe une machine avec ce contact et nom
     *
     * @param nom Nom de la pièce
     * @param idContact Id du contact
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existMachineByNomAndIdContact(String nom, Long idContact);
}
