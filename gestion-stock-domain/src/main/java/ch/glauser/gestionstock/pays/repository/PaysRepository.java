package ch.glauser.gestionstock.pays.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.model.Pays;

/**
 * Repository de gestion des payss
 */
public interface PaysRepository {
    /**
     * Récupère un pays
     *
     * @param id Id du pays à récupérer
     * @return La pays ou null
     */
    Pays getPays(Long id);

    /**
     * Récupère les pays
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de pays paginée
     */
    SearchResult<Pays> searchPays(SearchRequest searchRequest);

    /**
     * Crée un pays
     *
     * @param pays Pays à créer
     * @return Le pays créé
     */
    Pays createPays(Pays pays);

    /**
     * Modifie un pays
     *
     * @param pays Pays à modifier avec les nouvelles valeurs
     * @return Le pays modifié
     */
    Pays modifyPays(Pays pays);

    /**
     * Supprime un pays
     *
     * @param id Id du pays à supprimer
     */
    void deletePays(Long id);

    /**
     * Vérifie s'il existe un pays avec ce nom
     *
     * @param nom Nom du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existPaysByNom(String nom);

    /**
     * Vérifie s'il existe un pays avec cette abréviation
     *
     * @param abreviation Abréviation du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existPaysByAbreviation(String abreviation);
}
