package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;

/**
 * Repository de gestion des fournisseurs
 */
public interface FournisseurRepository {
    /**
     * Récupère un fournisseur
     *
     * @param id Id du fournisseur à récupérer
     * @return La fournisseur ou null
     */
    Fournisseur getFournisseur(Long id);

    /**
     * Récupère les fournisseurs
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de fournisseur paginée
     */
    SearchResult<Fournisseur> searchFournisseur(SearchRequest searchRequest);

    /**
     * Crée un fournisseur
     *
     * @param fournisseur Fournisseur à créer
     * @return Le fournisseur créé
     */
    Fournisseur createFournisseur(Fournisseur fournisseur);

    /**
     * Modifie un fournisseur
     *
     * @param fournisseur Fournisseur à modifier avec les nouvelles valeurs
     * @return Le fournisseur modifié
     */
    Fournisseur modifyFournisseur(Fournisseur fournisseur);

    /**
     * Supprime un fournisseur
     *
     * @param id Id du fournisseur à supprimer
     */
    void deleteFournisseur(Long id);
}
