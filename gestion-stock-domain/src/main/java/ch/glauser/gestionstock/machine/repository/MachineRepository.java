package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Machine;

import java.util.Optional;

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
    Optional<Machine> get(Long id);

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

    /**
     * Vérifie s'il existe une machine avec cette identité
     *
     * @param id Id du propriétaire
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByIdProprietaire(Long id);

    /**
     * Vérifie s'il existe une machine avec cette pièce
     *
     * @param id Id de la pièce
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByIdPiece(Long id);

    /**
     * Vérifie s'il existe une machine avec cette identité et nom
     *
     * @param nom Nom de la pièce
     * @param idProprietaire Id du propriétaire
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByNomAndIdProprietaire(String nom, Long idProprietaire);
}
