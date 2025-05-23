package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.identite.dto.PersonneMoraleDto;

/**
 * Service applicatif de gestion des personnes morales
 */
public interface PersonneMoraleApplicationService {
    /**
     * Récupère une personne morale
     *
     * @param id Id du personneMorale à récupérer
     * @return Le personneMorale ou null
     */
    PersonneMoraleDto get(Long id);

    /**
     * Crée une personne morale
     *
     * @param personneMorale Personne morale à créer
     * @return La personne morale créée
     */
    PersonneMoraleDto create(PersonneMoraleDto personneMorale);

    /**
     * Modifie une personne morale
     *
     * @param personneMorale Personne morale à modifier avec les nouvelles valeurs
     * @return La personne morale modifiée
     */
    PersonneMoraleDto modify(PersonneMoraleDto personneMorale);

    /**
     * Supprime une personne morale
     *
     * @param id Id de la personne morale à supprimer
     */
    void delete(Long id);
}
