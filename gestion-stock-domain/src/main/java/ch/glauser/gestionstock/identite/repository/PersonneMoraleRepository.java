package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.model.PersonneMorale;

import java.util.Optional;

/**
 * Repository de gestion des personnes morales
 */
public interface PersonneMoraleRepository {
    /**
     * Récupère une personne morale
     *
     * @param id Id de la personne morale à récupérer
     * @return La personne morale ou null
     */
    Optional<PersonneMorale> get(Long id);

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
