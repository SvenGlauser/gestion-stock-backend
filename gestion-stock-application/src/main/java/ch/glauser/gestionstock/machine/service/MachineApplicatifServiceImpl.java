package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des machines
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineApplicatifServiceImpl implements MachineApplicatifService {

    public static final String FIELD_MACHINE = "machine";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final MachineService machineService;

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).MACHINE_LECTEUR.name())")
    public MachineDto get(Long id) {
        Validation.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Machine machine = this.machineService.get(id);

        return Optional.ofNullable(machine).map(MachineDto::new).orElse(null);
    }

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).MACHINE_LECTEUR.name())")
    public SearchResult<MachineDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Machine> searchResult = this.machineService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, MachineDto::new);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).MACHINE_EDITEUR.name())")
    public MachineDto create(MachineDto machine) {
        Validation.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        Machine newMachine = machine.toDomain();

        Machine savedMachine = this.machineService.create(newMachine);

        return Optional.ofNullable(savedMachine).map(MachineDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).MACHINE_EDITEUR.name())")
    public MachineDto modify(MachineDto machine) {
        Validation.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        Machine machineToUpdate = machine.toDomain();

        Machine savedMachine = this.machineService.modify(machineToUpdate);

        return Optional.ofNullable(savedMachine).map(MachineDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).MACHINE_EDITEUR.name())")
    public void delete(Long id) {
        Validation.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.machineService.delete(id);
    }
}
