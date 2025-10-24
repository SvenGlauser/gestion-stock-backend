package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;

import java.util.Set;

/**
 * Service métier de gestion des fournisseurs
 */
public interface FournisseurService {
    /**
     * Récupère un fournisseur
     *
     * @param id Id du fournisseur à récupérer
     * @return Le fournisseur ou null
     */
    Fournisseur get(Long id);

    /**
     * Récupère un fournisseur
     *
     * @param designation Designation de l'identité liée au fournisseur (Nom prénom ou Raison sociale)
     * @return Le fournisseur ou null
     */
    Set<Fournisseur> findAllByIdentiteDesignation(String designation);

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
}
