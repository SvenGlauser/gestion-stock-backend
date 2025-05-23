package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;

/**
 * Service applicatif de gestion des personnes physiques
 */
public interface PersonnePhysiqueApplicationService {
    /**
     * Récupère une personne physique
     *
     * @param id Id du personnePhysique à récupérer
     * @return Le personnePhysique ou null
     */
    PersonnePhysiqueDto get(Long id);

    /**
     * Crée une personne physique
     *
     * @param personnePhysique Personne physique à créer
     * @return La personne physique créée
     */
    PersonnePhysiqueDto create(PersonnePhysiqueDto personnePhysique);

    /**
     * Modifie une personne physique
     *
     * @param personnePhysique Personne physique à modifier avec les nouvelles valeurs
     * @return La personne physique modifiée
     */
    PersonnePhysiqueDto modify(PersonnePhysiqueDto personnePhysique);

    /**
     * Supprime une personne physique
     *
     * @param id Id de la personne physique à supprimer
     */
    void delete(Long id);
}
