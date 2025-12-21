package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.identite.dto.PersonneMoraleDto;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.identite.model.PersonneMoraleConstantes;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des personnes morales
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonneMoraleApplicationServiceImpl implements PersonneMoraleApplicationService {

    private final PersonneMoraleService personneMoraleService;

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).IDENTITE_LECTEUR.name())")
    public PersonneMoraleDto get(Long id) {
        Validation.of(PersonneMoraleApplicationServiceImpl.class)
                .validateNotNull(id, PersonneMoraleConstantes.FIELD_ID)
                .execute();

        PersonneMorale personneMorale = this.personneMoraleService.get(id);

        return Optional.ofNullable(personneMorale).map(PersonneMoraleDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).IDENTITE_EDITEUR.name())")
    public PersonneMoraleDto create(PersonneMoraleDto personneMorale) {
        Validation.of(PersonneMoraleApplicationServiceImpl.class)
                .validateNotNull(personneMorale,  PersonneMoraleConstantes.FIELD_PERSONNE_MORALE)
                .execute();

        PersonneMorale newPersonneMorale = personneMorale.toDomain();

        PersonneMorale savedCategorie = this.personneMoraleService.create(newPersonneMorale);

        return Optional.ofNullable(savedCategorie).map(PersonneMoraleDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).IDENTITE_EDITEUR.name())")
    public PersonneMoraleDto modify(PersonneMoraleDto personneMorale) {
        Validation.of(PersonneMoraleApplicationServiceImpl.class)
                .validateNotNull(personneMorale, PersonneMoraleConstantes.FIELD_PERSONNE_MORALE)
                .execute();

        PersonneMorale personneMoraleToUpdate = personneMorale.toDomain();

        PersonneMorale savedCategorie = this.personneMoraleService.modify(personneMoraleToUpdate);

        return Optional.ofNullable(savedCategorie).map(PersonneMoraleDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).IDENTITE_EDITEUR.name())")
    public void delete(Long id) {
        Validation.of(PersonneMoraleApplicationServiceImpl.class)
                .validateNotNull(id, PersonneMoraleConstantes.FIELD_ID)
                .execute();

        this.personneMoraleService.delete(id);
    }
}
