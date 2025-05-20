package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;

import java.util.Optional;

/**
 * Repository de gestion des personnes physiques
 */
public interface PersonnePhysiqueRepository {
    /**
     * Récupère une personne physique
     *
     * @param id Id de la personne physique à récupérer
     * @return La personne physique ou null
     */
    Optional<PersonnePhysique> get(Long id);

    /**
     * Crée une personne physique
     *
     * @param personnePhysique Personne physique à créer
     * @return La personne physique créée
     */
    PersonnePhysique create(PersonnePhysique personnePhysique);

    /**
     * Modifie une personne physique
     *
     * @param personnePhysique Personne physique à modifier avec les nouvelles valeurs
     * @return La personne physique modifiée
     */
    PersonnePhysique modify(PersonnePhysique personnePhysique);

    /**
     * Supprime une personne physique
     *
     * @param id Id de la personne physique à supprimer
     */
    void delete(Long id);
}
