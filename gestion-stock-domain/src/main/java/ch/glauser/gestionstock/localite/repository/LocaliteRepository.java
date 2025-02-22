package ch.glauser.gestionstock.localite.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.model.Localite;

/**
 * Repository de gestion des localités
 */
public interface LocaliteRepository {
    /**
     * Récupère une localité
     *
     * @param id Id de la localité à récupérer
     * @return La localité ou null
     */
    Localite getLocalite(Long id);

    /**
     * Récupère les localités
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de localité paginée
     */
    SearchResult<Localite> searchLocalite(SearchRequest searchRequest);

    /**
     * Crée une localité
     *
     * @param localite Localité à créer
     * @return La localité créée
     */
    Localite createLocalite(Localite localite);

    /**
     * Modifie une localité
     *
     * @param localite Localité à modifier avec les nouvelles valeurs
     * @return La localité modifiée
     */
    Localite modifyLocalite(Localite localite);

    /**
     * Supprime une localité
     *
     * @param id Id de la localité à supprimer
     */
    void deleteLocalite(Long id);

    /**
     * Vérifie s'il existe une localité avec ce pays
     *
     * @param id Id du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existLocaliteByIdPays(Long id);

    /**
     * Vérifie s'il existe une localité avec ce pays, nom, npa
     *
     * @param npa NPA de la localité
     * @param nom Nom de la localité
     * @param id Id du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existLocaliteByNpaAndNomAndIdPays(String npa, String nom, Long id);
}
