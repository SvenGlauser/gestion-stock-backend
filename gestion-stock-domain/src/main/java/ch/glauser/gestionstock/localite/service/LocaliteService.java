package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.model.Localite;

/**
 * Service métier de gestion des localités
 */
public interface LocaliteService {
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
}
