package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.identite.model.PersonneMoraleConstantes;
import ch.glauser.gestionstock.identite.repository.PersonneMoraleRepository;
import ch.glauser.gestionstock.validation.common.Validation;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des personnes morales
 */
@RequiredArgsConstructor
public class PersonneMoraleServiceImpl implements PersonneMoraleService {

    private final PersonneMoraleRepository personneMoraleRepository;

    private final IdentiteService identiteService;

    @Override
    public PersonneMorale get(Long id) {
        Validation.of(PersonneMoraleServiceImpl.class)
                .validateNotNull(id, PersonneMoraleConstantes.FIELD_ID)
                .execute();

        return this.personneMoraleRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, PersonneMorale.class));
    }

    @Override
    public PersonneMorale create(PersonneMorale personneMorale) {
        Validation.of(PersonneMoraleServiceImpl.class)
                .validateNotNull(personneMorale, PersonneMoraleConstantes.FIELD_PERSONNE_MORALE)
                .execute();

        personneMorale.validateCreate().execute();

        return this.personneMoraleRepository.create(personneMorale);
    }

    @Override
    public PersonneMorale modify(PersonneMorale personneMorale) {
        Validation.of(PersonneMoraleServiceImpl.class)
                .validateNotNull(personneMorale, PersonneMoraleConstantes.FIELD_PERSONNE_MORALE)
                .execute();

        this.validateExist(personneMorale.getId(), ModifyWithInexistingIdException::new);
        personneMorale.validateModify().execute();

        return this.personneMoraleRepository.modify(personneMorale);
    }

    @Override
    public void delete(Long id) {
        Validation.of(PersonneMoraleServiceImpl.class)
                .validateNotNull(id, PersonneMoraleConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.identiteService.validateDelete(id);

        this.personneMoraleRepository.delete(id);
    }

    /**
     * Valide que la personne morale existe
     * @param id Id de la personne morale à supprimer
     * @param exception L'exception à instancier
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.personneMoraleRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, PersonneMorale.class));
    }
}
