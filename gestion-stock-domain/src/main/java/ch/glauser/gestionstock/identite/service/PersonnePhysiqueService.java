package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;

/**
 * Service métier de gestion des personnes physiques
 */
public interface PersonnePhysiqueService {
    /**
     * Récupère une personne physique
     *
     * @param id Id de la personne physique à récupérer
     * @return La personne physique
     */
    PersonnePhysique get(Long id);

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
