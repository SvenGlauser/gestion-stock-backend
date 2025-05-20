package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.identite.model.PersonneMorale;

/**
 * Service métier de gestion des personnes morales
 */
public interface PersonneMoraleService {
    /**
     * Récupère une personne morale
     *
     * @param id Id de la personne morale à récupérer
     * @return La personne morale
     */
    PersonneMorale get(Long id);

    /**
     * Crée une personne morale
     *
     * @param personneMorale Personne morale à créer
     * @return La personne morale créée
     */
    PersonneMorale create(PersonneMorale personneMorale);

    /**
     * Modifie une personne morale
     *
     * @param personneMorale Personne morale à modifier avec les nouvelles valeurs
     * @return La personne morale modifiée
     */
    PersonneMorale modify(PersonneMorale personneMorale);

    /**
     * Supprime une personne morale
     *
     * @param id Id de la personne morale à supprimer
     */
    void delete(Long id);
}
