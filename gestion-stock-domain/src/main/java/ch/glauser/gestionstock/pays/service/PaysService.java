package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.model.Pays;

/**
 * Service métier de gestion des payss
 */
public interface PaysService {
    /**
     * Récupère un pays
     *
     * @param id Id du pays à récupérer
     * @return Le pays ou null
     */
    Pays get(Long id);

    /**
     * Récupère un pays
     *
     * @param abreviation Abréviation du pays à récupérer
     * @return Le pays ou null
     */
    Pays getByAbreviation(String abreviation);

    /**
     * Récupère les pays
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de pays paginée
     */
    SearchResult<Pays> search(SearchRequest searchRequest);

    /**
     * Crée un pays
     *
     * @param pays Pays à créer
     * @return Le pays créé
     */
    Pays create(Pays pays);

    /**
     * Modifie un pays
     *
     * @param pays Pays à modifier avec les nouvelles valeurs
     * @return Le pays modifié
     */
    Pays modify(Pays pays);

    /**
     * Supprime un pays
     *
     * @param id Id du pays à supprimer
     */
    void delete(Long id);
}
