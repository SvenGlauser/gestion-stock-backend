package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;

/**
 * Service applicatif de gestion des localités
 */
public interface LocaliteApplicationService {
    /**
     * Récupère une localité
     *
     * @param id Id de la localité à récupérer
     * @return La localité ou null
     */
    LocaliteDto getLocalite(Long id);

    /**
     * Récupère les localités
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de localité paginée
     */
    SearchResult<LocaliteDto> searchLocalite(SearchRequest searchRequest);

    /**
     * Crée une localité
     *
     * @param localite Localité à créer
     * @return La localité créée
     */
    LocaliteDto createLocalite(LocaliteDto localite);

    /**
     * Modifie une localité
     *
     * @param localite Localité à modifier avec les nouvelles valeurs
     * @return La localité modifiée
     */
    LocaliteDto modifyLocalite(LocaliteDto localite);

    /**
     * Supprime une localité
     *
     * @param id Id de la localité à supprimer
     */
    void deleteLocalite(Long id);
}
