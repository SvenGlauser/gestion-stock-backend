package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.identite.model.PersonnePhysiqueConstantes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des personnes physiques
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonnePhysiqueApplicationServiceImpl implements PersonnePhysiqueApplicationService {

    private final PersonnePhysiqueService personnePhysiqueService;

    @Override
    public PersonnePhysiqueDto get(Long id) {
        Validation.of(PersonnePhysiqueApplicationServiceImpl.class)
                .validateNotNull(id, PersonnePhysiqueConstantes.FIELD_ID)
                .execute();

        PersonnePhysique personnePhysique = this.personnePhysiqueService.get(id);

        return Optional.ofNullable(personnePhysique).map(PersonnePhysiqueDto::new).orElse(null);
    }

    @Override
    @Transactional
    public PersonnePhysiqueDto create(PersonnePhysiqueDto personnePhysique) {
        Validation.of(PersonnePhysiqueApplicationServiceImpl.class)
                .validateNotNull(personnePhysique,  PersonnePhysiqueConstantes.FIELD_PERSONNE_PHYSIQUE)
                .execute();

        PersonnePhysique newPersonnePhysique = personnePhysique.toDomain();

        PersonnePhysique savedCategorie = this.personnePhysiqueService.create(newPersonnePhysique);

        return Optional.ofNullable(savedCategorie).map(PersonnePhysiqueDto::new).orElse(null);
    }

    @Override
    @Transactional
    public PersonnePhysiqueDto modify(PersonnePhysiqueDto personnePhysique) {
        Validation.of(PersonnePhysiqueApplicationServiceImpl.class)
                .validateNotNull(personnePhysique, PersonnePhysiqueConstantes.FIELD_PERSONNE_PHYSIQUE)
                .execute();

        PersonnePhysique personnePhysiqueToUpdate = personnePhysique.toDomain();

        PersonnePhysique savedCategorie = this.personnePhysiqueService.modify(personnePhysiqueToUpdate);

        return Optional.ofNullable(savedCategorie).map(PersonnePhysiqueDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Validation.of(PersonnePhysiqueApplicationServiceImpl.class)
                .validateNotNull(id, PersonnePhysiqueConstantes.FIELD_ID)
                .execute();

        this.personnePhysiqueService.delete(id);
    }
}
