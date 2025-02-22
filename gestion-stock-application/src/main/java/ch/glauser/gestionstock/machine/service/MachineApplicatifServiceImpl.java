package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.machine.model.Machine;
import lombok.RequiredArgsConstructor;
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
    public MachineDto getMachine(Long id) {
        Validator.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Machine machine = this.machineService.getMachine(id);

        return Optional.ofNullable(machine).map(MachineDto::new).orElse(null);
    }

    @Override
    public SearchResult<MachineDto> searchMachine(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Machine> searchResult = this.machineService.searchMachine(searchRequest);

        return SearchResultUtils.transformDto(searchResult, MachineDto::new);
    }

    @Override
    @Transactional
    public MachineDto createMachine(MachineDto machine) {
        Validator.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        Machine newMachine = machine.toDomain();

        newMachine.validateCreate();

        Machine savedMachine = this.machineService.createMachine(newMachine);

        return Optional.ofNullable(savedMachine).map(MachineDto::new).orElse(null);
    }

    @Override
    @Transactional
    public MachineDto modifyMachine(MachineDto machine) {
        Validator.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        Machine machineToUpdate = machine.toDomain();

        machineToUpdate.validateModify();

        Machine savedMachine = this.machineService.modifyMachine(machineToUpdate);

        return Optional.ofNullable(savedMachine).map(MachineDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void deleteMachine(Long id) {
        Validator.of(MachineApplicatifServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.machineService.deleteMachine(id);
    }
}
