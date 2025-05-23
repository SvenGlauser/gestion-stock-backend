package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;

import java.util.Optional;

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
    Optional<Fournisseur> get(Long id);

    /**
     * Récupère les fournisseurs
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de fournisseur paginée
     */
    SearchResult<Fournisseur> search(SearchRequest searchRequest);

    /**
     * Crée un fournisseur
     *
     * @param fournisseur Fournisseur à créer
     * @return Le fournisseur créé
     */
    Fournisseur create(Fournisseur fournisseur);

    /**
     * Modifie un fournisseur
     *
     * @param fournisseur Fournisseur à modifier avec les nouvelles valeurs
     * @return Le fournisseur modifié
     */
    Fournisseur modify(Fournisseur fournisseur);

    /**
     * Supprime un fournisseur
     *
     * @param id Id du fournisseur à supprimer
     */
    void delete(Long id);

    /**
     * Vérifie s'il existe un fournisseur avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existByIdLocalite(Long id);

    /**
     * Vérifie s'il existe un fournisseur avec ce nom
     *
     * @param nom Nom du fournisseur
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existByNom(String nom);
}
