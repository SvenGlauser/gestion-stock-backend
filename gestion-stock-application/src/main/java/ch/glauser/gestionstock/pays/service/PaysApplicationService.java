package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.dto.PaysDto;

/**
 * Service applicatif de gestion des pays
 */
public interface PaysApplicationService {
    /**
     * Récupère un pays
     *
     * @param id Id du pays à récupérer
     * @return Le pays ou null
     */
    PaysDto getPays(Long id);

    /**
     * Récupère les pays
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de pays paginée
     */
    SearchResult<PaysDto> searchPays(SearchRequest searchRequest);

    /**
     * Crée un pays
     *
     * @param pays Pays à créer
     * @return Le pays créé
     */
    PaysDto createPays(PaysDto pays);

    /**
     * Modifie un pays
     *
     * @param pays Pays à modifier avec les nouvelles valeurs
     * @return Le pays modifié
     */
    PaysDto modifyPays(PaysDto pays);

    /**
     * Supprime un pays
     *
     * @param id Id du pays à supprimer
     */
    void deletePays(Long id);
}
