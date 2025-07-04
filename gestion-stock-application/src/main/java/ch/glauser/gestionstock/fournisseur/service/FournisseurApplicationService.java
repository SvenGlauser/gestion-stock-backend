package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;

/**
 * Service applicatif de gestion des fournisseurs
 */
public interface FournisseurApplicationService {
    /**
     * Récupère un fournisseur
     *
     * @param id Id du fournisseur à récupérer
     * @return Le fournisseur ou null
     */
    FournisseurDto get(Long id);

    /**
     * Récupère les fournisseurs
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de fournisseur paginée
     */
    SearchResult<FournisseurDto> search(SearchRequest searchRequest);

    /**
     * Crée un fournisseur
     *
     * @param fournisseur Fournisseur à créer
     * @return Le fournisseur créé
     */
    FournisseurDto create(FournisseurDto fournisseur);

    /**
     * Modifie un fournisseur
     *
     * @param fournisseur Fournisseur à modifier avec les nouvelles valeurs
     * @return Le fournisseur modifié
     */
    FournisseurDto modify(FournisseurDto fournisseur);

    /**
     * Supprime un fournisseur
     *
     * @param id Id du fournisseur à supprimer
     */
    void delete(Long id);
}
