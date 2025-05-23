package ch.glauser.gestionstock.pays.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.model.Pays;

import java.util.Optional;

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
    Optional<Pays> get(Long id);

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

    /**
     * Vérifie s'il existe un pays avec ce nom
     *
     * @param nom Nom du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByNom(String nom);

    /**
     * Vérifie s'il existe un pays avec cette abréviation
     *
     * @param abreviation Abréviation du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByAbreviation(String abreviation);
}
