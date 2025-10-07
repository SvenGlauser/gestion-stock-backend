package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.identite.model.PersonnePhysiqueConstantes;
import ch.glauser.gestionstock.identite.repository.PersonnePhysiqueRepository;
import ch.glauser.gestionstock.validation.common.Validation;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des personnes physiques
 */
@RequiredArgsConstructor
public class PersonnePhysiqueServiceImpl implements PersonnePhysiqueService {

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final IdentiteService identiteService;

    @Override
    public PersonnePhysique get(Long id) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(id, PersonnePhysiqueConstantes.FIELD_ID)
                .execute();

        return this.personnePhysiqueRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, PersonnePhysique.class));
    }

    @Override
    public PersonnePhysique create(PersonnePhysique personnePhysique) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(personnePhysique, PersonnePhysiqueConstantes.FIELD_PERSONNE_PHYSIQUE)
                .execute();

        personnePhysique.validateCreate().execute();

        return this.personnePhysiqueRepository.create(personnePhysique);
    }

    @Override
    public PersonnePhysique modify(PersonnePhysique personnePhysique) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(personnePhysique, PersonnePhysiqueConstantes.FIELD_PERSONNE_PHYSIQUE)
                .execute();

        this.validateExist(personnePhysique.getId(), ModifyWithInexistingIdException::new);
        personnePhysique.validateModify().execute();

        return this.personnePhysiqueRepository.modify(personnePhysique);
    }

    @Override
    public void delete(Long id) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(id, PersonnePhysiqueConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.identiteService.validateDelete(id);

        this.personnePhysiqueRepository.delete(id);
    }

    /**
     * Valide que la personne physique existe
     * @param id Id de la personne physique à supprimer
     * @param exception L'exception à instancier
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.personnePhysiqueRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, PersonnePhysique.class));
    }
}
